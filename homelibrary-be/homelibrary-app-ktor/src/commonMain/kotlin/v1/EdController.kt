package com.otus.otuskotlin.homelibrary.app.ktor.v1

import io.ktor.server.application.*
import com.otus.otuskotlin.homelibrary.api.v1.models.*
import com.otus.otuskotlin.homelibrary.app.ktor.HmlrAppSettings
import kotlin.reflect.KClass

val EdCreate: KClass<*> = ApplicationCall::createEd::class
suspend fun ApplicationCall.createEd(appSettings: HmlrAppSettings) =
    processV1<EdCreateRequest, EdCreateResponse>(appSettings, EdCreate,"create")

val EdRead: KClass<*> = ApplicationCall::readEd::class
suspend fun ApplicationCall.readEd(appSettings: HmlrAppSettings) =
    processV1<EdReadRequest, EdReadResponse>(appSettings, EdRead, "read")

val EdUpdate: KClass<*> = ApplicationCall::updateEd::class
suspend fun ApplicationCall.updateEd(appSettings: HmlrAppSettings) =
    processV1<EdUpdateRequest, EdUpdateResponse>(appSettings, EdUpdate, "update")

val EdDelete: KClass<*> = ApplicationCall::deleteEd::class
suspend fun ApplicationCall.deleteEd(appSettings: HmlrAppSettings) =
    processV1<EdDeleteRequest, EdDeleteResponse>(appSettings, EdDelete, "delete")

val EdSearch: KClass<*> = ApplicationCall::searchEd::class
suspend fun ApplicationCall.searchEd(appSettings: HmlrAppSettings) =
    processV1<EdSearchRequest, EdSearchResponse>(appSettings, EdSearch, "search")
