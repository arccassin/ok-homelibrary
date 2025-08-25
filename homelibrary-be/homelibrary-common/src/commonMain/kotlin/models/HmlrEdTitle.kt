package com.otus.otuskotlin.homelibrary.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class HmlrEdTitle(private val title: String) {
    fun asString() = title

    companion object {
        val NONE = HmlrEdTitle("")
    }
}
