package com.otus.otuskotlin.homelibrary.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class HmlrEdAuthor(private val author: String) {
    fun asString() = author

    companion object {
        val NONE = HmlrEdAuthor("")
    }
}
