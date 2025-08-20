package com.otus.otuskotlin.homelibrary.biz.stub

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.Test
import kotlin.test.assertEquals

class EdDeleteStubTest {

    private val processor = HmlrEdProcessor()
    val ed = HmlrEd(
        id = HmlrEdId("666"),
    )

    val stubEd = HmlrEdStub.get()

    @Test
    fun delete() = runTest {

        val ctx = HmlrContext(
            command = HmlrCommand.DELETE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.SUCCESS,
            edRequest = ed,
        )
        processor.exec(ctx)

        assertEquals(ed.id, ctx.edResponse.id)
        assertEquals(stubEd.title, ctx.edResponse.title)
        assertEquals(stubEd.author, ctx.edResponse.author)
        assertEquals(stubEd.isbn, ctx.edResponse.isbn)
        assertEquals(stubEd.year, ctx.edResponse.year)
    }

    @Test
    fun badId() = runTest {
        val ctx = HmlrContext(
            command = HmlrCommand.DELETE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.BAD_ID,
            edRequest = HmlrEd(),
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun canNotDelete() = runTest {
        val ctx = HmlrContext(
            command = HmlrCommand.DELETE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.CANNOT_DELETE,
            edRequest = HmlrEd(),
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = HmlrContext(
            command = HmlrCommand.DELETE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.DB_ERROR,
            edRequest = ed,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = HmlrContext(
            command = HmlrCommand.DELETE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.BAD_TITLE,
            edRequest = ed,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
