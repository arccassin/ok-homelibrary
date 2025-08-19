package com.otus.otuskotlin.homelibrary.biz.stubs

import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrError
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs

fun ICorChainDsl<HmlrContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки базы данных
    """.trimIndent()
    on { stubCase == HmlrStubs.DB_ERROR && state == HmlrState.RUNNING }
    handle {
        fail(
            HmlrError(
                group = "internal",
                code = "internal-db",
                message = "Internal DB error"
            )
        )
    }
}
