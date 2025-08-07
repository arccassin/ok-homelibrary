package com.otus.otuskotlin.homelibrary.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.homelibrary.app.ktor.HmlrAppSettings
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings

fun Application.initAppSettings(): HmlrAppSettings {
    val corSettings = HmlrCorSettings(
        loggerProvider = getLoggerProviderConf(),
    )
    return HmlrAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = HmlrEdProcessor(corSettings),
    )
}
