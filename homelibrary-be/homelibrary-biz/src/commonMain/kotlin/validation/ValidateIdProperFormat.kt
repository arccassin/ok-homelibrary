package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.errorValidation
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в HmlrEdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z#:-]+$")
    on { edValidated.id != HmlrEdId.NONE && !edValidated.id.asString().matches(regExp) }
    handle {
        val encodedId = edValidated.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}
