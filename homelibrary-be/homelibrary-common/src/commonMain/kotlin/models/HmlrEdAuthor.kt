package com.otus.otuskotlin.homelibrary.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class HmlrEdAuthor(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = HmlrEdAuthor("")
    }
}
