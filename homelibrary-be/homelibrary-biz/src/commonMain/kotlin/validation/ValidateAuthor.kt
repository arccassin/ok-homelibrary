package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validateAuthor(title: String, regex: Regex) = worker {
    this.title = title
    on { edValidated.author.asString().isEmpty() || !edValidated.author.asString().contains(regex) }
    handle {
        validateStringField("author", edValidated.author.asString(), regex)
    }
}
