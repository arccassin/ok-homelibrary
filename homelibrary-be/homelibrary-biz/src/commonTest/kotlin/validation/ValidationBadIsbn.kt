package com.otus.otuskotlin.homelibrary.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIsbnCorrect(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get(),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("978-3-16-148410-0", ctx.edValidated.isbn.asString())
}

fun validationIsbnTrim(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { isbn = HmlrEdIsbn(" \n\t 978-3-16-148410-0 \t\n ") }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("978-3-16-148410-0", ctx.edValidated.isbn.asString())
}

fun validationIsbnSymbols(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { isbn = HmlrEdIsbn("!@#$%-^&*(),.{}") }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("isbn", error?.field)
    assertContains(error?.message ?: "", "isbn")
}

fun validationIsbnAlfabetSymbols(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { isbn = HmlrEdIsbn("9783ad1614841a00") }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("isbn", error?.field)
    assertContains(error?.message ?: "", "isbn")
}
