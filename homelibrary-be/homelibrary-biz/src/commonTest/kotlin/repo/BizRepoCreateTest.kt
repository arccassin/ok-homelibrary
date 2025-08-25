package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.backend.repo.tests.EdRepositoryMock
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val command = HmlrCommand.CREATE
    private val initEd = HmlrEdStub.get().apply {
        id = HmlrEdId("123")
        title = HmlrEdTitle("abc")
        author = HmlrEdAuthor("def")
    }
    private val repo = EdRepositoryMock(
        invokeCreateEd = {
            DbEdResponseOk(
                data = initEd
            )
        }
    )
    private val settings = HmlrCorSettings(
        repoTest = repo
    )
    private val processor = HmlrEdProcessor(settings)

    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = HmlrContext(
            command = command,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.TEST,
            edRequest = initEd,
        )
        processor.exec(ctx)
        assertEquals(HmlrState.FINISHING, ctx.state)
        assertNotEquals(HmlrEdId.NONE, ctx.edResponse.id)
        assertEquals(initEd.title, ctx.edResponse.title)
        assertEquals(initEd.author, ctx.edResponse.author)
        assertEquals(initEd.isbn, ctx.edResponse.isbn)
        assertEquals(initEd.year, ctx.edResponse.year)
    }
}
