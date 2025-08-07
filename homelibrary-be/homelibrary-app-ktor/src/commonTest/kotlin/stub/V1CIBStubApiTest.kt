package com.otus.otuskotlin.homelibrary.app.ktor.stub

import com.otus.otuskotlin.homelibrary.api.v1.apiV1Mapper
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import com.otus.otuskotlin.homelibrary.api.v1.models.*
import com.otus.otuskotlin.homelibrary.app.ktor.HmlrAppSettings
import com.otus.otuskotlin.homelibrary.app.ktor.module
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class V1EdStubApiTest {

    @Test
    fun create() = v1EdTestApplication(
        func = "create",
        request = EdCreateRequest(
            ed = EdCreateObject(
                title = "Бесы",
                author = "Федор Достоевский",
                isbn = "2312312123123",
                year = "1984"
            ),
            debug = EdDebug(
                mode = EdRequestDebugMode.STUB,
                stub = EdRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<EdCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("111", responseObj.ed?.id)
    }

    @Test
    fun read() = v1EdTestApplication(
        func = "read",
        request = EdReadRequest(
            ed = EdReadObject("111"),
            debug = EdDebug(
                mode = EdRequestDebugMode.STUB,
                stub = EdRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<EdReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("111", responseObj.ed?.id)
    }

    @Test
    fun update() = v1EdTestApplication(
        func = "update",
        request = EdUpdateRequest(
            ed = EdUpdateObject(
                id = "111",
                title = "Бесы",
                author = "Федор Достоевский",
                isbn = "2312312123123",
                year = "1984"
            ),
            debug = EdDebug(
                mode = EdRequestDebugMode.STUB,
                stub = EdRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<EdUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("111", responseObj.ed?.id)
        assertEquals("Бесы", responseObj.ed?.title)
        assertEquals("Федор Достоевский", responseObj.ed?.author)
        assertEquals("2312312123123", responseObj.ed?.isbn)
        assertEquals("1984", responseObj.ed?.year)
    }

    @Test
    fun delete() = v1EdTestApplication(
        func = "delete",
        request = EdDeleteRequest(
            ed = EdDeleteObject(
                id = "111",
                lock = "123"
            ),
            debug = EdDebug(
                mode = EdRequestDebugMode.STUB,
                stub = EdRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<EdDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("111", responseObj.ed?.id)
    }

    @Test
    fun search() = v1EdTestApplication(
        func = "search",
        request = EdSearchRequest(
            edFilter = EdSearchFilter(),
            debug = EdDebug(
                mode = EdRequestDebugMode.STUB,
                stub = EdRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<EdSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("111-01", responseObj.eds?.first()?.id)
    }

    private inline fun <reified T: IRequest> v1EdTestApplication(
        func: String,
        request: T,
        crossinline function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { module(HmlrAppSettings(corSettings = HmlrCorSettings())) }
        val client = createClient {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
        }

        val response = client.post("/ed/basic/$func") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(response)
    }
}
