package com.otus.otuskotlin.homelibrary.app.common

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.homelibrary.api.v1.mappers.*
import com.otus.otuskotlin.homelibrary.api.v1.models.*
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class ControllerV1Test {

    private val request = EdCreateRequest(
        ed = EdCreateObject(
            title = "Бесы",
            author = "Федор Достоевский",
            isbn = "2312312123123",
            year = "1984"
        ),
        debug = EdDebug(mode = EdRequestDebugMode.STUB, stub = EdRequestDebugStubs.SUCCESS)
    )

    private val appSettings: IHmlrAppSettings = object : IHmlrAppSettings {
        override val corSettings: HmlrCorSettings = HmlrCorSettings()
        override val processor: HmlrEdProcessor = HmlrEdProcessor(corSettings)
    }

    class TestApplicationCall(private val request: IRequest) {
        var res: IResponse? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : IRequest> receive(): T = request as T
        fun respond(res: IResponse) {
            this.res = res
        }
    }

    private suspend fun TestApplicationCall.createEdKtor(appSettings: IHmlrAppSettings) {
        val resp = appSettings.controllerHelper(
            { fromTransport(receive<EdCreateRequest>()) },
            { toTransportEd() },
            ControllerV1Test::class,
            "controller-v1-test"
        )
        respond(resp)
    }

    @Test
    fun ktorHelperTest() = runTest {
        val testApp = TestApplicationCall(request).apply { createEdKtor(appSettings) }
        val res = testApp.res as EdCreateResponse
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}
