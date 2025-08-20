package com.otus.otuskotlin.homelibrary.biz.stubs

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdAuthor
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdIsbn
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdTitle
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker
import com.otus.otuskotlin.homelibrary.logging.common.LogLevel
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub

fun ICorChainDsl<HmlrContext>.stubCreateSuccess(title: String, corSettings: HmlrCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания издания
    """.trimIndent()
    on { stubCase == HmlrStubs.SUCCESS && state == HmlrState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubCreatesSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = HmlrState.FINISHING
            val stub = HmlrEdStub.prepareResult {
                edRequest.title.takeIf { it != HmlrEdTitle.NONE }?.also { this.title = it }
                edRequest.author.takeIf { it != HmlrEdAuthor.NONE }?.also { this.author = it }
                edRequest.isbn.takeIf { it != HmlrEdIsbn.NONE }?.also { this.isbn = it }
                edRequest.year.takeIf { it.isNotBlank() }?.also { this.year = it }
            }
            edResponse = stub
        }
    }
}

