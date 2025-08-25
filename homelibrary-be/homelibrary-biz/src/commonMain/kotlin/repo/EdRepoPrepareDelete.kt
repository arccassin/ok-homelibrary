package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == HmlrState.RUNNING }
    handle {
        edRepoPrepare = edValidated.deepCopy()
    }
}
