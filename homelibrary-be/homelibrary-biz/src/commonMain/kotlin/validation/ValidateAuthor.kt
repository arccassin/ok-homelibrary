package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validateAuthor(title: String) = worker {
    this.title = title
    val regex = REG_EXP_CONTENT
    on { edValidated.author.asString().isEmpty() || !edValidated.author.asString().contains(regex) }
    handle {
        validateStringField("author", edValidated.author.asString(), regex)
    }
}
