package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand
import validation.validationLockCorrect
import validation.validationLockEmpty
import validation.validationLockFormat
import validation.validationLockTrim
import kotlin.test.Test

class BizValidationUpdateTest: BaseBizValidationTest() {
    override val command = HmlrCommand.UPDATE

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

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)

}
