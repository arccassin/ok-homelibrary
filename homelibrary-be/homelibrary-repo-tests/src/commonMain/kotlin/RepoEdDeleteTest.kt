package com.otus.otuskotlin.homelibrary.backend.repo.tests

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.common.repo.DbEdIdRequest
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErr
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErrWithData
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoEdDeleteTest {
    abstract val repo: EdRepoInitialized
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = HmlrEdId("ad-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteEd(DbEdIdRequest(deleteSucc.id, lock = lockOld))
        assertIs<DbEdResponseOk>(result)
        assertEquals(deleteSucc.title, result.data.title)
        assertEquals(deleteSucc.author, result.data.author)
        assertEquals(deleteSucc.isbn, result.data.isbn)
        assertEquals(deleteSucc.year, result.data.year)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readEd(DbEdIdRequest(notFoundId, lock = lockOld))

        assertIs<DbEdResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val result = repo.deleteEd(DbEdIdRequest(deleteConc.id, lock = lockBad))

        println(result)
        assertIs<DbEdResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertNotNull(error)
    }

    companion object : BaseInitEds("delete") {
        override val initObjects: List<HmlrEd> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }
}
