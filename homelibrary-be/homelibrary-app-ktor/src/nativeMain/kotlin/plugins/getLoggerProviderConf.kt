package com.otus.otuskotlin.homelibrary.app.ktor.plugins

import com.otus.otuskotlin.homelibrary.logging.common.HlLoggerProvider
import io.ktor.server.application.*
import com.otus.otuskotlin.homelibrary.logging.kermit.hlLoggerKermit

actual fun Application.getLoggerProviderConf(): HlLoggerProvider =
    HlLoggerProvider { hlLoggerKermit(it) }
