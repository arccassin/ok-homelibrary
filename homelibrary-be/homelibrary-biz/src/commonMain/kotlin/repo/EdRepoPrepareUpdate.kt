package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == HmlrState.RUNNING }
    handle {
        edRepoPrepare = edRepoRead.deepCopy().apply {
            this.title = edValidated.title
            author = edValidated.author
            isbn = edValidated.isbn
            year = edValidated.year
            lock = edValidated.lock
        }
    }
}
