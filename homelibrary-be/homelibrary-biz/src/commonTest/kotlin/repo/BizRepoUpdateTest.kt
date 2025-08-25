package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.backend.repo.tests.EdRepositoryMock
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlinx.coroutines.test.runTest
import repo.repoNotFoundTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoUpdateTest: RepoBaseTest() {

    private val command = HmlrCommand.UPDATE

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val edToUpdate = HmlrEdStub.get().apply {
            id = HmlrEdId("123")
            title = HmlrEdTitle("xyz")
            author = HmlrEdAuthor("xyz")
        }
        val ctx = HmlrContext(
            command = command,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.TEST,
            edRequest = edToUpdate,
        )
        processor.exec(ctx)
        assertEquals(HmlrState.FINISHING, ctx.state)
        assertEquals(edToUpdate.id, ctx.edResponse.id)
        assertEquals(initEd.title, ctx.edResponse.title)
        assertEquals(initEd.author, ctx.edResponse.author)
        assertEquals(initEd.isbn, ctx.edResponse.isbn)
        assertEquals(initEd.year, ctx.edResponse.year)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
