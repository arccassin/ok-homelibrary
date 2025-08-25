package com.otus.otuskotlin.homelibrary.backend.repo.tests

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdTitle
import com.otus.otuskotlin.homelibrary.common.repo.*
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class EdRepositoryMockTest {
    private val repo = EdRepositoryMock(
        invokeCreateEd = { DbEdResponseOk(HmlrEdStub.prepareResult { title = HmlrEdTitle("create") }) },
        invokeReadEd = { DbEdResponseOk(HmlrEdStub.prepareResult { title = HmlrEdTitle("read") }) },
        invokeUpdateEd = { DbEdResponseOk(HmlrEdStub.prepareResult { title = HmlrEdTitle("update") }) },
        invokeDeleteEd = { DbEdResponseOk(HmlrEdStub.prepareResult { title = HmlrEdTitle("delete") }) },
        invokeSearchEd = { DbEdsResponseOk(listOf(HmlrEdStub.prepareResult { title = HmlrEdTitle("search") })) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createEd(DbEdRequest(HmlrEd()))
        assertIs<DbEdResponseOk>(result)
        assertEquals(HmlrEdTitle("create"), result.data.title)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readEd(DbEdIdRequest(HmlrEd()))
        assertIs<DbEdResponseOk>(result)
        assertEquals(HmlrEdTitle("read"), result.data.title)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateEd(DbEdRequest(HmlrEd()))
        assertIs<DbEdResponseOk>(result)
        assertEquals(HmlrEdTitle("update"), result.data.title)
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteEd(DbEdIdRequest(HmlrEd()))
        assertIs<DbEdResponseOk>(result)
        assertEquals(HmlrEdTitle("delete"), result.data.title)
    }

    @Test
    fun mockSearch() = runTest {
        val result = repo.searchEd(DbEdFilterRequest())
        assertIs<DbEdsResponseOk>(result)
        assertEquals(HmlrEdTitle("search"), result.data.first().title)
    }

}
