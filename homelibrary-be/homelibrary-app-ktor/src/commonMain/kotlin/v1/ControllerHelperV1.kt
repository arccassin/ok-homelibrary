package com.otus.otuskotlin.homelibrary.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import com.otus.otuskotlin.homelibrary.api.v1.mappers.*
import com.otus.otuskotlin.homelibrary.api.v1.models.IRequest
import com.otus.otuskotlin.homelibrary.api.v1.models.IResponse
import com.otus.otuskotlin.homelibrary.app.common.controllerHelper
import com.otus.otuskotlin.homelibrary.app.ktor.HmlrAppSettings
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1(
    appSettings: HmlrAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    {
        fromTransport(this@processV1.receive<Q>())
    },
    { this@processV1.respond(toTransportEd() as R) },
    clazz,
    logId,
)