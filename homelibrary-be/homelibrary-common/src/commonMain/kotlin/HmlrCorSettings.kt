package com.otus.otuskotlin.homelibrary.common

import com.otus.otuskotlin.homelibrary.logging.common.HlLoggerProvider

data class HmlrCorSettings(
    val loggerProvider: HlLoggerProvider = HlLoggerProvider(),
) {
    companion object {
        val NONE = HmlrCorSettings()
    }
}
