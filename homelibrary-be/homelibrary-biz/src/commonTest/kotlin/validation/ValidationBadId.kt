package com.otus.otuskotlin.homelibrary.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIdCorrect(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get()
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("666", ctx.edValidated.id.asString())

}

fun validationIdTrim(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { id = HmlrEdId(" \n\t 123-234-abc-ABC \n\t ") }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("123-234-abc-ABC", ctx.edValidated.id.asString())
}

fun validationIdEmpty(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { id = HmlrEdId.NONE }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationIdFormat(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { id = HmlrEdId("!@#\$%^&*(),.{}") }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
