package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validateTitle(title: String) = worker {
    this.title = title
    val regex = REG_EXP_CONTENT
    on { edValidated.title.asString().isEmpty() || !edValidated.title.asString().contains(regex) }
    handle {
        validateStringField("title", edValidated.title.asString(), regex)
    }
}
