package com.otus.otuskotlin.homelibrary.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationAuthorCorrect(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get()
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("Федор Достоевский", ctx.edValidated.author.asString())
}

fun validationAuthorTrim(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest = HmlrEdStub.get().apply { author = HmlrEdAuthor(" \n\t   abc \n\t") }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(HmlrState.FAILING, ctx.state)
    assertEquals("abc", ctx.edValidated.author.asString())
}

fun validationAuthorEmpty(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest =  HmlrEdStub.get().apply { author = HmlrEdAuthor.NONE }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("author", error?.field)
    assertContains(error?.message ?: "", "author")
}

fun validationAuthorSymbols(command: HmlrCommand, processor: HmlrEdProcessor) = runTest {
    val ctx = HmlrContext(
        command = command,
        state = HmlrState.NONE,
        workMode = HmlrWorkMode.TEST,
        edRequest =  HmlrEdStub.get().apply { author = HmlrEdAuthor("!@#$%^&*(),.{}") }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(HmlrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("author", error?.field)
    assertContains(error?.message ?: "", "author")
}
