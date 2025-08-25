package com.otus.otuskotlin.homelibrary.backend.repo.postgresql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import com.otus.otuskotlin.homelibrary.common.models.*

class EdTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val title = text(SqlFields.TITLE).nullable()
    val author = text(SqlFields.AUTHOR).nullable()
    val isbn = text(SqlFields.ISBN).nullable()
    val year = text(SqlFields.YEAR).nullable()
    val owner = text(SqlFields.OWNER_ID)
    val lock = text(SqlFields.LOCK)
    val productId = text(SqlFields.PRODUCT_ID).nullable()

    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = HmlrEd(
        id = HmlrEdId(res[id].toString()),
        title = HmlrEdTitle(res[title].toString()),
        author = res[author]?.let { HmlrEdAuthor(it)} ?: HmlrEdAuthor.NONE,
        isbn = res[isbn]?.let { HmlrEdIsbn(it) } ?: HmlrEdIsbn.NONE,
        year = res[year] ?: "",
        ownerId = HmlrUserId(res[owner].toString()),
        lock = HmlrEdLock(res[lock]),
    )

    fun to(it: UpdateBuilder<*>, ed: HmlrEd, randomUuid: () -> String) {
        it[id] = ed.id.takeIf { it != HmlrEdId.NONE }?.asString() ?: randomUuid()
        it[title] = ed.title.takeIf { it != HmlrEdTitle.NONE }?.asString()
        it[author] = ed.author.takeIf { it != HmlrEdAuthor.NONE }?.asString()
        it[isbn] = ed.isbn.takeIf { it != HmlrEdIsbn.NONE }?.asString()
        it[year] = ed.year.takeIf { it.isNotBlank() }
        it[owner] = ed.ownerId.asString()
        it[lock] = ed.lock.takeIf { it != HmlrEdLock.NONE }?.asString() ?: randomUuid()
    }

}

