package com.otus.otuskotlin.homelibrary.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.repo.*
import com.otus.otuskotlin.homelibrary.common.repo.exceptions.RepoEmptyLockException
import com.otus.otuskotlin.homelibrary.repo.common.IRepoEdInitializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class EdRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : EdRepoBase(), IRepoEd, IRepoEdInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, EdEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(eds: Collection<HmlrEd>) = eds.map { ed ->
        val entity = EdEntity(ed)
        require(entity.id != null)
        cache.put(entity.id, entity)
        ed
    }

    override suspend fun createEd(rq: DbEdRequest): IDbEdResponse = tryEdMethod {
        val key = randomUuid()
        val ad = rq.ed.copy(id = HmlrEdId(key), lock = HmlrEdLock(randomUuid()))
        val entity = EdEntity(ad)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbEdResponseOk(ad)
    }

    override suspend fun readEd(rq: DbEdIdRequest): IDbEdResponse = tryEdMethod {
        val key = rq.id.takeIf { it != HmlrEdId.NONE }?.asString() ?: return@tryEdMethod errorEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbEdResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateEd(rq: DbEdRequest): IDbEdResponse = tryEdMethod {
        val rqEd = rq.ed
        val id = rqEd.id.takeIf { it != HmlrEdId.NONE } ?: return@tryEdMethod errorEmptyId
        val key = id.asString()
        val oldLock = rqEd.lock.takeIf { it != HmlrEdLock.NONE } ?: return@tryEdMethod errorEmptyLock(id)

        mutex.withLock {
            val oldEd = cache.get(key)?.toInternal()
            when {
                oldEd == null -> errorNotFound(id)
                oldEd.lock == HmlrEdLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldEd.lock != oldLock -> errorRepoConcurrency(oldEd, oldLock)
                else -> {
                    val newEd = rqEd.copy(lock = HmlrEdLock(randomUuid()))
                    val entity = EdEntity(newEd)
                    cache.put(key, entity)
                    DbEdResponseOk(newEd)
                }
            }
        }
    }


    override suspend fun deleteEd(rq: DbEdIdRequest): IDbEdResponse = tryEdMethod {
        val id = rq.id.takeIf { it != HmlrEdId.NONE } ?: return@tryEdMethod errorEmptyId
        val key = id.asString()
        val oldLock = rq.lock.takeIf { it != HmlrEdLock.NONE } ?: return@tryEdMethod errorEmptyLock(id)

        mutex.withLock {
            val oldEd = cache.get(key)?.toInternal()
            when {
                oldEd == null -> errorNotFound(id)
                oldEd.lock == HmlrEdLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldEd.lock != oldLock -> errorRepoConcurrency(oldEd, oldLock)
                else -> {
                    cache.invalidate(key)
                    DbEdResponseOk(oldEd)
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchEd(rq: DbEdFilterRequest): IDbEdsResponse = tryEdsMethod {
        val result: List<HmlrEd> = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != HmlrUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbEdsResponseOk(result)
    }
}
