package com.otus.otuskotlin.homelibrary.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class HmlrEdIsbn(private val isbn: String) {
    fun asString() = isbn

    companion object {
        val NONE = HmlrEdIsbn("")
    }
}
