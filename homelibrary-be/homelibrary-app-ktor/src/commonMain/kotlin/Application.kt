package com.otus.otuskotlin.homelibrary.app.ktor

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.otus.otuskotlin.homelibrary.api.v1.apiV1Mapper
import com.otus.otuskotlin.homelibrary.app.ktor.plugins.initAppSettings
import com.otus.otuskotlin.homelibrary.app.ktor.v1.ed

fun Application.module(
    appSettings: HmlrAppSettings = initAppSettings()
) {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        /* TODO
            Это временное решение, оно опасно.
            В боевом приложении здесь должны быть конкретные настройки
        */
        anyHost()
    }

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("ci") {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
            ed(appSettings)
        }
    }
}
