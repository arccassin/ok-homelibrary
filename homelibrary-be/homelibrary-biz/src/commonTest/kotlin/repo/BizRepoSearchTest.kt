package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.backend.repo.tests.EdRepositoryMock
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.repo.DbEdsResponseOk
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val command = HmlrCommand.SEARCH
    private val initEd = HmlrEdStub.get().apply {
        id = HmlrEdId("123")
        title = HmlrEdTitle("abc")
        author = HmlrEdAuthor("def")
    }
    private val repo = EdRepositoryMock(
        invokeSearchEd = {
            DbEdsResponseOk(
                data = listOf(initEd),
            )
        }
    )
    private val settings = HmlrCorSettings(repoTest = repo)
    private val processor = HmlrEdProcessor(settings)

    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = HmlrContext(
            command = command,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.TEST,
            edFilterRequest = HmlrEdFilter(
                searchString = "abc",
            ),
        )
        processor.exec(ctx)
        assertEquals(HmlrState.FINISHING, ctx.state)
        assertEquals(1, ctx.edsResponse.size)
    }
}
