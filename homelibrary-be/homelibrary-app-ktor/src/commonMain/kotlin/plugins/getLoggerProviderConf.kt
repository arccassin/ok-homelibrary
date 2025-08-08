package com.otus.otuskotlin.homelibrary.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.homelibrary.logging.common.HlLoggerProvider

expect fun Application.getLoggerProviderConf(): HlLoggerProvider