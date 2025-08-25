package repo

import com.otus.otuskotlin.homelibrary.backend.repo.tests.EdRepositoryMock
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.common.repo.errorNotFound
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val initEd = HmlrEdStub.get().apply {
    id = HmlrEdId("123")
    title = HmlrEdTitle("abc")
    author = HmlrEdAuthor("def")
}

private val repo = EdRepositoryMock(
    invokeReadEd = {
        if (it.id == initEd.id) {
            DbEdResponseOk(
                data = initEd,
            )
        } else errorNotFound(it.id)
    }
)
private val settings = HmlrCorSettings(repoTest = repo)
private val processor = HmlrEdProcessor(settings)

fun repoNotFoundTest(command: HmlrCommand) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply {
            id = HmlrEdId("123")
            title = HmlrEdTitle("abc")
            author = HmlrEdAuthor("def")
        },
    )
    processor.exec(ctx)
    assertEquals(HmlrState.FAILING, ctx.state)
    assertEquals(HmlrEd(), ctx.edResponse)
    assertEquals(1, ctx.errors.size)
    assertNotNull(ctx.errors.find { it.code == "repo-not-found" }, "Errors must contain not-found")
}
