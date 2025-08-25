package com.otus.otuskotlin.homelibrary.backend.repo.tests

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.common.models.HmlrError
import com.otus.otuskotlin.homelibrary.common.repo.DbEdIdRequest
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErr
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoEdReadTest {
    abstract val repo: EdRepoInitialized
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readEd(DbEdIdRequest(readSucc.id))

        assertIs<DbEdResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        println("REQUESTING")
        val result = repo.readEd(DbEdIdRequest(notFoundId))
        println("RESULT: $result")

        assertIs<DbEdResponseErr>(result)
        println("ERRORS: ${result.errors}")
        val error: HmlrError? = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitEds("read") {
        override val initObjects: List<HmlrEd> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = HmlrEdId("ed-repo-read-notFound")

    }
}
