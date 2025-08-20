package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validateLock(title: String) = worker {
    this.title = title
    val regex = REG_EXP_ID
    on { edValidated.lock.asString().isEmpty() || !edValidated.lock.asString().contains(regex) }
    handle {
        validateStringField("lock", edValidated.lock.asString(), regex)
    }
}

