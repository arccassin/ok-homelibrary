package com.otus.otuskotlin.homelibrary.biz.stubs

import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrError
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs

fun ICorChainDsl<HmlrContext>.stubCanNotDelete(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки удаления
    """.trimIndent()
    on { stubCase == HmlrStubs.CANNOT_DELETE && state == HmlrState.RUNNING }
    handle {
        fail(
            HmlrError(
                group = "internal",
                code = "internal-can-not-delete",
                message = "Internal can not delete"
            )
        )
    }
}
