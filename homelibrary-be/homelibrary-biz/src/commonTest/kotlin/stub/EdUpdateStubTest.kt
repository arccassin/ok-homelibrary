package com.otus.otuskotlin.homelibrary.biz.stub

import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class EdUpdateStubTest {

    private val processor = HmlrEdProcessor()
    val ed = HmlrEdStub.get().apply { id = HmlrEdId("666") }

    @Test
    fun updateCIB() = runTest {

        val ctx = HmlrContext(
            command = HmlrCommand.UPDATE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.SUCCESS,
            edRequest = ed,
        )
        processor.exec(ctx)
        assertEquals(ed.id, ctx.edResponse.id)
        assertEquals(ed.title, ctx.edResponse.title)
        assertEquals(ed.author, ctx.edResponse.author)
        assertEquals(ed.isbn, ctx.edResponse.isbn)
        assertEquals(ed.year, ctx.edResponse.year)
    }

    @Test
    fun badTitle() = runTest {

        val ctx = HmlrContext(
            command = HmlrCommand.UPDATE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.BAD_TITLE,
            edRequest = ed,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badId() = runTest {

        val ctx = HmlrContext(
            command = HmlrCommand.UPDATE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.BAD_ID,
            edRequest = ed,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badAuthor() = runTest {

        val ctx = HmlrContext(
            command = HmlrCommand.UPDATE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.BAD_AUTHOR,
            edRequest = ed,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("author", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badIsbn() = runTest {

        val ctx = HmlrContext(
            command = HmlrCommand.UPDATE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.BAD_ISBN,
            edRequest = ed,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("isbn", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badYear() = runTest {

        val ctx = HmlrContext(
            command = HmlrCommand.UPDATE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.BAD_YEAR,
            edRequest = ed,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("year", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {

        val ctx = HmlrContext(
            command = HmlrCommand.UPDATE,
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
            command = HmlrCommand.UPDATE,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.BAD_SEARCH_STRING,
            edRequest = ed,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}