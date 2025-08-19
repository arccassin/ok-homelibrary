package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validateId(title: String, regex: Regex) = worker {
    this.title = title
    on { edValidated.id.asString().isEmpty() || !edValidated.id.asString().contains(regex) }
    handle {
        validateStringField("id", edValidated.id.asString(), regex)
    }
}
