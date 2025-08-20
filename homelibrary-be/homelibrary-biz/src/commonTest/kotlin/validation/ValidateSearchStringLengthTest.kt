package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.api.log.mapper.toLog
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdFilter
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.cor.rootChain
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateSearchStringLengthTest {
    @Test
    fun emptyString() = runTest {
        val ctx = HmlrContext(
            state = HmlrState.RUNNING,
            edFilterValidated = HmlrEdFilter(searchString = "")
        )
        chain.exec(ctx)
        assertEquals(HmlrState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun blankString() = runTest {
        val ctx = HmlrContext(
            state = HmlrState.RUNNING,
            edFilterValidated = HmlrEdFilter(searchString = "   ")
        )
        ctx.logger.log(data = ctx.toLog("blankString"))
        chain.exec(ctx)
        ctx.logger.log(data = ctx.toLog("blankString"))
        assertEquals(HmlrState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun shortString() = runTest {
        val ctx = HmlrContext(
            state = HmlrState.RUNNING,
            edFilterValidated = HmlrEdFilter(searchString = "12")
        )
        chain.exec(ctx)
        assertEquals(HmlrState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooShort", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = HmlrContext(
            state = HmlrState.RUNNING,
            edFilterValidated = HmlrEdFilter(searchString = "123")
        )
        chain.exec(ctx)
        assertEquals(HmlrState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun longString() = runTest {
        val ctx = HmlrContext(
            state = HmlrState.RUNNING,
            edFilterValidated = HmlrEdFilter(searchString = "12".repeat(51))
        )
        chain.exec(ctx)
        assertEquals(HmlrState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooLong", ctx.errors.first().code)
    }

    companion object {
        val chain = rootChain {
            validateSearchStringLength("")
        }.build()
    }
}
