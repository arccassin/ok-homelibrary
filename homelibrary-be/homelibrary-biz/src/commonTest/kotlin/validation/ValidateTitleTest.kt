package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.api.log.mapper.toLog
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdTitle
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.cor.rootChain
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateTitleTest {
    @Test
    fun emptyString() = runTest {
        val ctx = HmlrContext(
            state = HmlrState.RUNNING,
            edValidated = HmlrEd(title = HmlrEdTitle.NONE)
        )
        chain.exec(ctx)
        assertEquals(HmlrState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-title-empty", ctx.errors.first().code)
    }

    @Test
    fun noContent() = runTest {
        val ctx = HmlrContext(
            state = HmlrState.RUNNING,
            edValidated = HmlrEd(title = HmlrEdTitle("12!@#$%^&*()_+-="))
        )
        chain.exec(ctx)
        assertEquals(HmlrState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-title-noContent", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = HmlrContext(
            state = HmlrState.RUNNING,
            edValidated = HmlrEd(title = HmlrEdTitle("R"))
        )
        chain.exec(ctx)
        ctx.logger.log(data = ctx.toLog("normalString"))
        assertEquals(HmlrState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    companion object {
        val chain = rootChain<HmlrContext> {
            validateTitle("Валидация title", REG_EXP_ID)
        }.build()
    }
}
