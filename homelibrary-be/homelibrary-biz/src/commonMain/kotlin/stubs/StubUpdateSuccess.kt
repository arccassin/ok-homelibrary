package com.otus.otuskotlin.homelibrary.biz.stubs

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker
import com.otus.otuskotlin.homelibrary.logging.common.LogLevel
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub

fun ICorChainDsl<HmlrContext>.stubUpdateSuccess(title: String, corSettings: HmlrCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для изменения издания
    """.trimIndent()
    on { stubCase == HmlrStubs.SUCCESS && state == HmlrState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubOffersSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = HmlrState.FINISHING
            val stub = HmlrEdStub.prepareResult {
                edRequest.id.takeIf { it != HmlrEdId.NONE }?.also { this.id = it }
                edRequest.title.takeIf { it != HmlrEdTitle.NONE }?.also { this.title = it }
                edRequest.author.takeIf { it != HmlrEdAuthor.NONE }?.also { this.author = it }
                edRequest.isbn.takeIf { it != HmlrEdIsbn.NONE }?.also { this.isbn = it }
                edRequest.year.takeIf { it.isNotBlank() }?.also { this.year = it }
            }
            edResponse = stub
        }
    }
}
