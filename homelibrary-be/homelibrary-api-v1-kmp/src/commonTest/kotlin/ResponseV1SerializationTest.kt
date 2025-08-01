package com.otus.otuskotlin.homelibrary.api.v1

import com.otus.otuskotlin.homelibrary.api.v1.models.EdCreateResponse
import com.otus.otuskotlin.homelibrary.api.v1.models.EdResponseObject
import com.otus.otuskotlin.homelibrary.api.v1.models.IResponse
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response: IResponse = EdCreateResponse(
        ed = EdResponseObject(
            title = "ed title",
            author = "ed author",
            isbn = "ed isbn",
            year = "ed year"
        )
    )

    @Test
    fun serialize() {
//        val json = apiV1Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV1Mapper.encodeToString(RequestSerializers.create, request)
        val json = apiV1Mapper.encodeToString(response)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"ed title\""))
        assertContains(json, Regex("\"author\":\\s*\"ed author\""))
        assertContains(json, Regex("\"isbn\":\\s*\"ed isbn\""))
        assertContains(json, Regex("\"year\":\\s*\"ed year\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(response)
        val obj = apiV1Mapper.decodeFromString<IResponse>(json) as EdCreateResponse

        assertEquals(response, obj)
    }
}
