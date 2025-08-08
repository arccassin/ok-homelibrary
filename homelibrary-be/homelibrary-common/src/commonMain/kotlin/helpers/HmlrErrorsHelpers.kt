package com.otus.otuskotlin.homelibrary.common.helpers

import com.otus.otuskotlin.homelibrary.common.models.HmlrError

fun Throwable.asHmlrError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = HmlrError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)
