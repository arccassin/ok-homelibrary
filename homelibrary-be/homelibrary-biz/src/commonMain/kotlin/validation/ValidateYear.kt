package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.errorValidation
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun ICorChainDsl<HmlrContext>.validateYear(title: String) = worker {
    val currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    this.title = title
    on {
        val intYear = edValidated.year.toIntOrNull()
        edValidated.year.isNotEmpty() && edValidated.year != "0000"
        (intYear == null
                || intYear > currentYear
                || intYear < -3500)
    }
    handle {
        val intYear = edValidated.year.toIntOrNull()
        if (intYear == null)
            fail(
                errorValidation(
                    field = "year",
                    violationCode = "notNumeric",
                    description = "field must be numeric"
                )
            )
        else if (intYear > currentYear)
            fail(
                errorValidation(
                    field = "year",
                    violationCode = "inFuture",
                    description = "field must not be in future"
                )
            )
        else if (intYear < -3500)
            fail(
                errorValidation(
                    field = "year",
                    violationCode = "farInPast",
                    description = "field must not be earlier than -3500"
                )
            )
    }
}
