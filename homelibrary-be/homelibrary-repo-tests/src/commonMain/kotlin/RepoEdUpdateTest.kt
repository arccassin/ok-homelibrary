package com.otus.otuskotlin.homelibrary.backend.repo.tests

import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.repo.*
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoEdUpdateTest {
    abstract val repo: EdRepoInitialized
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = HmlrEdId("ed-repo-update-not-found")
    protected val lockBad = HmlrEdLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = HmlrEdLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        HmlrEdStub.get().apply {
            id = updateSucc.id
            title = HmlrEdTitle("update object")
            lock = initObjects.first().lock
        }
    }
    private val reqUpdateNotFound = HmlrEdStub.get().apply {
        id = updateIdNotFound
        title = HmlrEdTitle("update object not found")
        lock = initObjects.first().lock
    }

    private val reqUpdateConc by lazy {
        HmlrEdStub.get().apply {
            id = updateConc.id
            title = HmlrEdTitle("update object not found")
            lock = lockBad
    }
}

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateEd(DbEdRequest(reqUpdateSucc))
        println("ERRORS: ${(result as? DbEdResponseErr)?.errors}")
        println("ERRORSWD: ${(result as? DbEdResponseErrWithData)?.errors}")
        assertIs<DbEdResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.title, result.data.title)
        assertEquals(reqUpdateSucc.author, result.data.author)
        assertEquals(reqUpdateSucc.isbn, result.data.isbn)
        assertEquals(reqUpdateSucc.year, result.data.year)
        assertEquals(lockNew, result.data.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateEd(DbEdRequest(reqUpdateNotFound))
        assertIs<DbEdResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateEd(DbEdRequest(reqUpdateConc))
        assertIs<DbEdResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitEds("update") {
        override val initObjects: List<HmlrEd> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
