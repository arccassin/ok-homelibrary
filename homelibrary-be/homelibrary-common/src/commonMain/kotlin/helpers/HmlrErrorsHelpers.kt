package com.otus.otuskotlin.homelibrary.common.helpers

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrError
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.logging.common.LogLevel

fun Throwable.asHmlrError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = HmlrError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

inline fun HmlrContext.addError(vararg error: HmlrError) = errors.addAll(error)

inline fun HmlrContext.fail(error: HmlrError) {
    addError(error)
    state = HmlrState.FAILING
}

inline fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR,
) = HmlrError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)