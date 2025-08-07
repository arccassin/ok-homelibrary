package com.otus.otuskotlin.homelibrary.app.ktor.v1

import io.ktor.server.routing.*
import com.otus.otuskotlin.homelibrary.app.ktor.HmlrAppSettings
import io.ktor.server.application.call
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.ed(appSettings: HmlrAppSettings) {

    route("basic") {

        post("create") {
            call.createEd(appSettings)
        }
        post("read") {
            call.readEd(appSettings)
        }
        post("update") {
            call.updateEd(appSettings)
        }
        post("delete") {
            call.deleteEd(appSettings)
        }
        post("search") {
            call.searchEd(appSettings)
        }
    }
}