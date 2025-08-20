package com.otus.otuskotlin.homelibrary.biz.stub

import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class EdSearchStubTest {

    private val processor = HmlrEdProcessor()
    val filter = HmlrEdFilter(searchString = "тестовый фильтр")

    @Test
    fun search() = runTest {

        val ctx = HmlrContext(
            command = HmlrCommand.SEARCH,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.SUCCESS,
            edFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.edsResponse.size > 1)
        val first = ctx.edsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.asString().contains(filter.searchString))
        assertTrue(first.author.asString().contains(filter.searchString))
        with(HmlrEdStub.get()) {
            assertEquals(isbn, first.isbn)
            assertEquals(year, first.year)
        }
    }

    @Test
    fun badSearchString() = runTest {
        val ctx = HmlrContext(
            command = HmlrCommand.SEARCH,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.BAD_SEARCH_STRING,
            edFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("search-string", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = HmlrContext(
            command = HmlrCommand.SEARCH,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.DB_ERROR,
            edFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = HmlrContext(
            command = HmlrCommand.SEARCH,
            state = HmlrState.NONE,
            workMode = HmlrWorkMode.STUB,
            stubCase = HmlrStubs.CANNOT_DELETE,
            edFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(HmlrEd(), ctx.edResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
