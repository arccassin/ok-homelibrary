package com.otus.otuskotlin.homelibrary.backend.repo.postgresql

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import com.otus.otuskotlin.homelibrary.common.helpers.asHmlrError
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.repo.*
import com.otus.otuskotlin.homelibrary.repo.common.IRepoEdInitializable

class RepoEdSql(
    properties: SqlProperties,
    private val randomUuid: () -> String = { uuid4().toString() }
) : IRepoEd, IRepoEdInitializable {
    private val edTable = EdTable("${properties.schema}.${properties.table}")

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    fun clear(): Unit = transaction(conn) {
        edTable.deleteAll()
    }

    private fun saveObj(ad: HmlrEd): HmlrEd = transaction(conn) {
        val res = edTable
            .insert {
                to(it, ad, randomUuid)
            }
            .resultedValues
            ?.map { edTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private suspend inline fun <T> transactionWrapper(crossinline block: () -> T, crossinline handle: (Exception) -> T): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbEdResponse): IDbEdResponse =
        transactionWrapper(block) { DbEdResponseErr(it.asHmlrError()) }

    override fun save(eds: Collection<HmlrEd>): Collection<HmlrEd> = eds.map { saveObj(it) }
    override suspend fun createEd(rq: DbEdRequest): IDbEdResponse = transactionWrapper {
        DbEdResponseOk(saveObj(rq.ed))
    }

    private fun read(id: HmlrEdId): IDbEdResponse {
        val res = edTable.selectAll().where {
            edTable.id eq id.asString()
        }.singleOrNull() ?: return errorNotFound(id)
        return DbEdResponseOk(edTable.from(res))
    }

    override suspend fun readEd(rq: DbEdIdRequest): IDbEdResponse = transactionWrapper { read(rq.id) }

    private suspend fun update(
        id: HmlrEdId,
        lock: HmlrEdLock,
        block: (HmlrEd) -> IDbEdResponse
    ): IDbEdResponse =
        transactionWrapper {
            if (id == HmlrEdId.NONE) return@transactionWrapper errorEmptyId

            val current = edTable.selectAll().where { edTable.id eq id.asString() }
                .singleOrNull()
                ?.let { edTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorRepoConcurrency(current, lock)
                else -> block(current)
            }
        }


    override suspend fun updateEd(rq: DbEdRequest): IDbEdResponse = update(rq.ed.id, rq.ed.lock) {
        edTable.update({ edTable.id eq rq.ed.id.asString() }) {
            to(it, rq.ed.copy(lock = HmlrEdLock(randomUuid())), randomUuid)
        }
        read(rq.ed.id)
    }

    override suspend fun deleteEd(rq: DbEdIdRequest): IDbEdResponse = update(rq.id, rq.lock) {
        edTable.deleteWhere { id eq rq.id.asString() }
        DbEdResponseOk(it)
    }

    override suspend fun searchEd(rq: DbEdFilterRequest): IDbEdsResponse =
        transactionWrapper({
            val res = edTable.selectAll().where {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != HmlrUserId.NONE) {
                        add(edTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (edTable.title like "%${rq.titleFilter}%")
                                    or (edTable.author like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbEdsResponseOk(data = res.map { edTable.from(it) })
        }, {
            DbEdsResponseErr(it.asHmlrError())
        })
}
