package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.backend.repo.tests.EdRepositoryMock
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand
import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdAuthor
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdTitle
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.models.HmlrWorkMode
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErr
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.common.repo.DbEdsResponseOk
import com.otus.otuskotlin.homelibrary.common.repo.errorNotFound
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

open class RepoBaseTest {
    val uuid = "10000000-0000-0000-0000-000000000001"
    val initEd = HmlrEdStub.get().apply {
        id = HmlrEdId("123")
        title = HmlrEdTitle("abc")
        author = HmlrEdAuthor("def")
    }
    val repo = EdRepositoryMock(
        invokeCreateEd = {
            DbEdResponseOk(
                data = HmlrEdStub.get().apply {
                    id = HmlrEdId(uuid)
                    title = it.ed.title
                    author = it.ed.author
                }
            )
        },
        invokeReadEd = {
            if (it.id == initEd.id) {
                DbEdResponseOk(
                    data = initEd
                )
            } else errorNotFound(it.id)
        },
        invokeDeleteEd = {
            if (it.id == initEd.id) {
                DbEdResponseOk(
                    data = initEd
                )
            } else DbEdResponseErr()
        },
        invokeUpdateEd = {
            DbEdResponseOk(
                data = HmlrEdStub.get().apply {
                    id = HmlrEdId("123")
                    title = HmlrEdTitle("xyz")
                    author = HmlrEdAuthor("xyz")
                }
            )
        },
        invokeSearchEd = {
            DbEdsResponseOk(
                data = listOf(initEd)
            )
        }
    )
    val settings = HmlrCorSettings(repoTest = repo)
    val processor = HmlrEdProcessor(settings)

    fun repoNotFoundTest(command: HmlrCommand) = runTest {
        val ctx = HmlrContext(
            command = command,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.TEST,
            edRequest = HmlrEdStub.get().apply {
                id = HmlrEdId("12345")
                title = HmlrEdTitle("xyz")
                author = HmlrEdAuthor("xyz")
            }
        )
        processor.exec(ctx)
        assertEquals(HmlrState.FAILING, ctx.state)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals(1, ctx.errors.size)
        assertNotNull(ctx.errors.find { it.code == "repo-not-found"}, "Errors must contain not-found")
    }
}