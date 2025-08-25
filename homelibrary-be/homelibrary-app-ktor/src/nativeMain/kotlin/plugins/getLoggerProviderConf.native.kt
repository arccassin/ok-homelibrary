package com.otus.otuskotlin.homelibrary.app.ktor.plugins

import com.otus.otuskotlin.homelibrary.logging.common.HlLoggerProvider
import io.ktor.server.application.*
import com.otus.otuskotlin.homelibrary.logging.kermit.hlLoggerKermit

actual fun Application.getLoggerProviderConf(): HlLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "kmp", null -> HlLoggerProvider { hlLoggerKermit(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp and socket")
    }