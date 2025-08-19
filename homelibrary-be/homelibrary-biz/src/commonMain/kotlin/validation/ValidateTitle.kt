package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validateTitle(title: String, regex: Regex) = worker {
    this.title = title
    on { edValidated.title.asString().isEmpty() || !edValidated.title.asString().contains(regex) }
    handle {
        validateStringField("title", edValidated.title.asString(), regex)
    }
}
