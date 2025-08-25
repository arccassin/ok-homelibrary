package com.otus.otuskotlin.homelibrary.app.ktor.repo

import com.otus.otuskotlin.homelibrary.api.v1.apiV1Mapper
import com.otus.otuskotlin.homelibrary.api.v1.models.EdRequestDebugMode
import com.otus.otuskotlin.homelibrary.app.ktor.HmlrAppSettings
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import com.otus.otuskotlin.homelibrary.api.v1.mappers.toTransportRead
import com.otus.otuskotlin.homelibrary.api.v1.mappers.toTransportcreateEd
import com.otus.otuskotlin.homelibrary.api.v1.mappers.toTransportdeleteEd
import com.otus.otuskotlin.homelibrary.api.v1.mappers.toTransportreadEd
import com.otus.otuskotlin.homelibrary.api.v1.mappers.toTransportupdateEd
import com.otus.otuskotlin.homelibrary.api.v1.models.*
import com.otus.otuskotlin.homelibrary.app.ktor.module
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdLock
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class V1EdRepoBaseTest {
    abstract val workMode: EdRequestDebugMode
    abstract val appSettingsCreate: HmlrAppSettings
    abstract val appSettingsRead:   HmlrAppSettings
    abstract val appSettingsUpdate: HmlrAppSettings
    abstract val appSettingsDelete: HmlrAppSettings
    abstract val appSettingsSearch: HmlrAppSettings

    protected val uuidOld = "10000000-0000-0000-0000-000000000001"
    protected val uuidNew = "10000000-0000-0000-0000-000000000002"
    protected val uuidSup = "10000000-0000-0000-0000-000000000003"
    protected val initEd = HmlrEdStub.prepareResult {
        id = HmlrEdId(uuidOld)
        lock = HmlrEdLock(uuidOld)
    }
    protected val initEdSupply = HmlrEdStub.prepareResult {
        id = HmlrEdId(uuidSup)
    }


    @Test
    fun create() {
        val ed = initEd.toTransportcreateEd()
        v1TestApplication(
            conf = appSettingsCreate,
            func = "create",
            request = EdCreateRequest(
                ed = ed,
                debug = EdDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<EdCreateResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidNew, responseObj.ed?.id)
            assertEquals(ed.title, responseObj.ed?.title)
            assertEquals(ed.author, responseObj.ed?.author)
            assertEquals(ed.isbn, responseObj.ed?.isbn)
            assertEquals(ed.year, responseObj.ed?.year)
        }
    }

    @Test
    fun read() {
        val ed = initEd.toTransportreadEd()
        v1TestApplication(
            conf = appSettingsRead,
            func = "read",
            request = EdReadRequest(
                ed = ed,
                debug = EdDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<EdReadResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidOld, responseObj.ed?.id)
        }
    }

    @Test
    fun update() {
        val ed = initEd.toTransportupdateEd()
        v1TestApplication(
            conf = appSettingsUpdate,
            func = "update",
            request = EdUpdateRequest(
               ed = ed,
                debug = EdDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<EdUpdateResponse>()
            assertEquals(200, response.status.value)
            assertEquals(ed.id, responseObj.ed?.id)
            assertEquals(ed.title, responseObj.ed?.title)
            assertEquals(ed.author, responseObj.ed?.author)
            assertEquals(ed.isbn, responseObj.ed?.isbn)
            assertEquals(ed.year, responseObj.ed?.year)
            assertEquals(uuidNew, responseObj.ed?.lock)
        }
    }
    @Test
    fun delete() {
        val ed = initEd.toTransportdeleteEd()
        v1TestApplication(
            conf = appSettingsDelete,
            func = "delete",
            request = EdDeleteRequest(
                ed = ed,
                debug = EdDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<EdDeleteResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidOld, responseObj.ed?.id)
        }
    }

    @Test
    fun search() = v1TestApplication(
        conf = appSettingsSearch,
        func = "search",
        request = EdSearchRequest(
            edFilter = EdSearchFilter(),
            debug = EdDebug(mode = workMode),
        ),
    ) { response ->
        val responseObj = response.body<EdSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.eds?.size)
        assertEquals(uuidOld, responseObj.eds?.first()?.id)
    }

    private inline fun <reified T: IRequest> v1TestApplication(
        conf: HmlrAppSettings,
        func: String,
        request: T,
        crossinline function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { module(appSettings = conf) }
        val client = createClient {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
        }
        val response = client.post("/v1/ed/$func") {
            contentType(ContentType.Application.Json)
            header("X-Trace-Id", "12345")
            setBody(request)
        }
        function(response)
    }
}
