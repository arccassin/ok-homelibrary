package com.otus.otuskotlin.homelibrary.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationYearCorrect(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get(),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("1984", ctx.edValidated.year)
}

fun validationYearTrim(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { year = " \n\t 2025 \t\n " }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("2025", ctx.edValidated.year)
}

fun validationYearNoneNumeric(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { year = "fg12" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("year", error?.field)
    assertContains(error?.message ?: "", "numeric")
}

fun validationYearInFuture(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { year = "9999" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("year", error?.field)
    assertContains(error?.message ?: "", "future")
}

fun validationYearInPast(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { year = "-4000" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("year", error?.field)
    assertContains(error?.message ?: "", "earlier")
}