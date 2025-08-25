package com.otus.otuskotlin.homelibrary.backend.repo.tests

import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.repo.DbEdRequest
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals


abstract class RepoEdCreateTest {
    abstract val repo: EdRepoInitialized
    protected open val lockNew = HmlrEdLock("20000000-0000-0000-0000-000000000002")

    private val createObj = HmlrEdStub.get().apply {
        title = HmlrEdTitle("create object title")
        id = HmlrEdId.NONE
    }

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createEd(DbEdRequest(createObj))
        val expected = createObj
        assertIs<DbEdResponseOk>(result)
        assertNotEquals(HmlrEdId.NONE, result.data.id)
        assertEquals(expected.title, result.data.title)
        assertEquals(expected.author, result.data.author)
        assertEquals(expected.isbn, result.data.isbn)
        assertEquals(expected.year, result.data.year)
    }

    companion object : BaseInitEds("create") {
        override val initObjects: List<HmlrEd> = emptyList()
    }
}
