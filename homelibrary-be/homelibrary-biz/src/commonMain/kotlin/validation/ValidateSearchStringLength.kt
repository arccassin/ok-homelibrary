package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.errorValidation
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.chain
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validateSearchStringLength(title: String) = chain {
    this.title = title
    this.description = """
        Валидация длины строки поиска в поисковых фильтрах. Допустимые значения:
        - null - не выполняем поиск по строке
        - 3-100 - допустимая длина
        - больше 100 - слишком длинная строка
    """.trimIndent()
    on { state == HmlrState.RUNNING }
    worker {
        this.title = "Проверка кейса длины на 0-2 символа"
        this.description = this.title
        on { state == HmlrState.RUNNING && edFilterValidated.searchString.length in (1..2) }
        handle {
            fail(
                errorValidation(
                    field = "searchString",
                    violationCode = "tooShort",
                    description = "Search string must contain at least 3 symbols"
                )
            )
        }
    }
    worker {
        this.title = "Проверка кейса длины на более 100 символов"
        this.description = this.title
        on { state == HmlrState.RUNNING && edFilterValidated.searchString.length > 100 }
        handle {
            fail(
                errorValidation(
                    field = "searchString",
                    violationCode = "tooLong",
                    description = "Search string must be no more than 100 symbols long"
                )
            )
        }
    }
}
