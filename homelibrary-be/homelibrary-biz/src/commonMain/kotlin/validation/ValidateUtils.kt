package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.errorValidation
import com.otus.otuskotlin.homelibrary.common.helpers.fail


val REG_EXP_ID = Regex("^[0-9a-zA-Z-:]+$")

/*
  Проверяем, что у нас есть какие-то слова в строке,
  а не только бессмысленные символы типа %^&^$^%#^))&^*&%^^&
*/
val REG_EXP_CONTENT = Regex("\\p{L}")

inline fun HmlrContext.validateStringField(fieldName: String, fieldValue: String, regex: Regex) {
    if (fieldValue.isEmpty()) {
        fail(
            errorValidation(
                field = fieldName,
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    } else if (!fieldValue.contains(regex))
        fail(
            errorValidation(
                field = fieldName,
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
}
