package com.otus.otuskotlin.homelibrary.api.v1.mappers

import com.otus.otuskotlin.homelibrary.api.v1.models.*
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperCreateTest {
    @Test
    fun fromTransport() {
        val req = EdCreateRequest(
            debug = EdDebug(
                mode = EdRequestDebugMode.STUB,
                stub = EdRequestDebugStubs.SUCCESS,
            ),
            ed = HmlrEdStub.get().toTransportcreateEd(),
        )
        val expected = HmlrEdStub.prepareResult {
            id = HmlrEdId.NONE
            ownerId = HmlrUserId.NONE
            lock = HmlrEdLock.NONE
            permissionsClient.clear()
        }

        val context = HmlrContext()
        context.fromTransport(req)

        assertEquals(HmlrStubs.SUCCESS, context.stubCase)
        assertEquals(HmlrWorkMode.STUB, context.workMode)
        assertEquals(expected, context.edRequest)
    }

    @Test
    fun toTransport() {
        val context = HmlrContext(
            requestId = HmlrRequestId("1234"),
            command = HmlrCommand.CREATE,
            edResponse =  HmlrEdStub.get(),
            errors = mutableListOf(
                HmlrError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = HmlrState.RUNNING,
        )

        val req = context.toTransportEd() as EdCreateResponse

        assertEquals( HmlrEdStub.get().toTransportEd(), req.ed)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
