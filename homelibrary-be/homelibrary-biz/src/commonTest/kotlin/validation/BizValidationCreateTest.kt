package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand
import kotlin.test.Test

// смотрим пример теста валидации, собранного из тестовых функций-оберток
class BizValidationCreateTest: BaseBizValidationTest() {
    override val command: HmlrCommand = HmlrCommand.CREATE

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test fun correctAuthor() = validationAuthorCorrect(command, processor)
    @Test fun trimAuthor() = validationAuthorTrim(command, processor)
    @Test fun emptyAuthor() = validationAuthorEmpty(command, processor)
    @Test fun badSymbolsAuthor() = validationAuthorSymbols(command, processor)

    @Test fun correctIsbn() = validationIsbnCorrect(command, processor)
    @Test fun trimIsbn() = validationIsbnTrim(command, processor)
    @Test fun badSymbolsIsbn() = validationIsbnSymbols(command, processor)
    @Test fun badNotDashIsbn() = validationIsbnAlfabetSymbols(command, processor)

    @Test fun correctYear() = validationYearCorrect(command, processor)
    @Test fun trimYear() = validationYearTrim(command, processor)
    @Test fun noneNumericYear() = validationYearNoneNumeric(command, processor)
    @Test fun inFutureYear() = validationYearInFuture(command, processor)
    @Test fun inPastYear() = validationYearInPast(command, processor)

}
