package com.otus.otuskotlin.homelibrary.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class HmlrUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = HmlrUserId("")
    }
}
