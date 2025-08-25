package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.models.HmlrWorkMode
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != HmlrWorkMode.STUB }
    handle {
        edResponse = edRepoDone
        edsResponse = edsRepoDone
        state = when (val st = state) {
            HmlrState.RUNNING -> HmlrState.FINISHING
            else -> st
        }
    }
}
