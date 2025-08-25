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

inline fun HmlrContext.addError(error: HmlrError) = errors.add(error)
inline fun HmlrContext.addErrors(error: Collection<HmlrError>) = errors.addAll(error)

inline fun HmlrContext.fail(error: HmlrError) {
    addError(error)
    state = HmlrState.FAILING
}
inline fun HmlrContext.fail(errors: Collection<HmlrError>) {
    addErrors(errors)
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

inline fun errorSystem(
    violationCode: String,
    level: LogLevel = LogLevel.ERROR,
    e: Throwable,
) = HmlrError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    level = level,
    exception = e,
)

inline fun UnExpectedDbError(type: String) = HmlrError(
    code = "db-$type",
    group = "db",
    message = "UnExpected Db $type"
)
