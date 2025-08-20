package com.otus.otuskotlin.homelibrary.biz.stubs

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrError
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.stubValidationBadIsbn(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для isbn издания
    """.trimIndent()
    on { stubCase == HmlrStubs.BAD_ISBN && state == HmlrState.RUNNING }
    handle {
        fail(
            HmlrError(
                group = "validation",
                code = "validation-isbn",
                field = "isbn",
                message = "Wrong isbn field"
            )
        )
    }
}
