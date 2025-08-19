package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validateIsbn(title: String, regex: Regex) = worker {
    this.title = title
    on { edValidated.isbn.asString().isNotEmpty() && !edValidated.isbn.asString().contains(regex) }
    handle {
        validateStringField("isbn", edValidated.isbn.asString(), regex)
    }
}

