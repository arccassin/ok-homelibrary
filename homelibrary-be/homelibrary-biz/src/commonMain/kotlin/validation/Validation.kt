package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.api.log.mapper.toLog
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.chain
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.validation(block: ICorChainDsl<HmlrContext>.() -> Unit) = chain {
    worker("Копируем поля в edValidated") {
        edValidated = edRequest.deepCopy()
        edFilterValidated = edFilterRequest.deepCopy()
    }

    worker("Нормализация данных") {
        logger.debug(
            msg = "Нормализация данных started",
            marker = "BIZ",
            data = this.toLog(command.toString())
        )

        edFilterValidated.searchString = edFilterValidated.searchString.trim()

        edValidated.id = HmlrEdId(edValidated.id.asString().trim())
        edValidated.title = HmlrEdTitle(edValidated.title.asString().trim())
        edValidated.author = HmlrEdAuthor(edValidated.author.asString().trim())
        edValidated.isbn = HmlrEdIsbn(edValidated.isbn.asString().trim())
        edValidated.year = edValidated.year.trim()
        edValidated.lock = HmlrEdLock(edValidated.lock.asString().trim())

        logger.debug(
            msg = "Нормализация данных finished",
            marker = "BIZ",
            data = this.toLog(command.toString())
        )
    }

    block()

    worker("Завершение валидации") {
        if (state == HmlrState.FAILING) {
            edValidated = HmlrEd()
            edFilterValidated = HmlrEdFilter()
        }

        logger.info(
            msg = "Завершение валидации finished",
            marker = "BIZ",
            data = this.toLog(command.toString())
        )
    }

    title = "Валидация"

    on { state == HmlrState.RUNNING }
}