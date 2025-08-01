package com.otus.otuskotlin.homelibrary.common.models

import com.otus.otuskotlin.homelibrary.logging.common.LogLevel


data class HmlrError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val level: LogLevel = LogLevel.ERROR,
    val exception: Throwable? = null,
)
