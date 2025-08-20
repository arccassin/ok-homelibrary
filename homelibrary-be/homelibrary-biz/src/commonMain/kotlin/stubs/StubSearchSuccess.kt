package com.otus.otuskotlin.homelibrary.biz.stubs

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker
import com.otus.otuskotlin.homelibrary.logging.common.LogLevel
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub

fun ICorChainDsl<HmlrContext>.stubSearchSuccess(title: String, corSettings: HmlrCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для поиска изданий
    """.trimIndent()
    on { stubCase == HmlrStubs.SUCCESS && state == HmlrState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubSearchSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = HmlrState.FINISHING
            edsResponse.addAll(HmlrEdStub.prepareSearchList(edFilterRequest.searchString))
        }
    }
}
