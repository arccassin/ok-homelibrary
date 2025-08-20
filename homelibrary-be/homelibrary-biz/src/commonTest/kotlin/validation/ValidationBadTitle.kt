package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdTitle
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.models.HmlrWorkMode
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationTitleCorrect(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get(),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("Бесы", ctx.edValidated.title.asString())
}

fun validationTitleTrim(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { title = HmlrEdTitle(" \n\t abc \t\n ") }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("abc", ctx.edValidated.title.asString())
}

fun validationTitleEmpty(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { title = HmlrEdTitle.NONE }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

fun validationTitleSymbols(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { title = HmlrEdTitle("!@#$%^&*(),.{}") }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}
