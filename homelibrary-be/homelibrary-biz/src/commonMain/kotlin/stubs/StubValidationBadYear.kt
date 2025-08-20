package com.otus.otuskotlin.homelibrary.biz.stubs

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrError
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker


fun ICorChainDsl<HmlrContext>.stubValidationBadYear(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для года издания
    """.trimIndent()

    on { stubCase == HmlrStubs.BAD_YEAR && state == HmlrState.RUNNING }
    handle {
        fail(
            HmlrError(
                group = "validation",
                code = "validation-year",
                field = "year",
                message = "Wrong year field"
            )
        )
    }
}
