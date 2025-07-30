package com.otus.otuskotlin.homelibrary.api.v1


import com.otus.otuskotlin.homelibrary.api.v1.models.EdCreateObject
import com.otus.otuskotlin.homelibrary.api.v1.models.EdCreateRequest
import com.otus.otuskotlin.homelibrary.api.v1.models.EdDebug
import com.otus.otuskotlin.homelibrary.api.v1.models.EdRequestDebugMode
import com.otus.otuskotlin.homelibrary.api.v1.models.EdRequestDebugStubs
import com.otus.otuskotlin.homelibrary.api.v1.models.IRequest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request: IRequest = EdCreateRequest(
        debug = EdDebug(
            mode = EdRequestDebugMode.STUB,
            stub = EdRequestDebugStubs.BAD_TITLE
        ),
        ed = EdCreateObject(
            title = "ed title",
            author = "ed author",
            isbn = "ed isbn",
            year = "ed year",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(IRequest.serializer(), request)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"ed title\""))
        assertContains(json, Regex("\"author\":\\s*\"ed author\""))
        assertContains(json, Regex("\"isbn\":\\s*\"ed isbn\""))
        assertContains(json, Regex("\"year\":\\s*\"ed year\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(request)
        val obj = apiV1Mapper.decodeFromString<IRequest>(json) as EdCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"ad": null}
        """.trimIndent()
        val obj = apiV1Mapper.decodeFromString<EdCreateRequest>(jsonString)

        assertEquals(null, obj.ed)
    }
}
