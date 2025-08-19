package com.otus.otuskotlin.homelibrary.biz.stubs

import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrError
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs

fun ICorChainDsl<HmlrContext>.stubValidationBadSearchString(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации поисковой строки
    """.trimIndent()
    on { stubCase == HmlrStubs.BAD_SEARCH_STRING && state == HmlrState.RUNNING }
    handle {
        fail(
            HmlrError(
                group = "validation",
                code = "validation-search-string",
                field = "search-string",
                message = "Wrong search-string field"
            )
        )
    }
}
