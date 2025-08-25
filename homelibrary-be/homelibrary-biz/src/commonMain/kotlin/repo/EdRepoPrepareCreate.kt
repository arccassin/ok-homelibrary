package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub

fun ICorChainDsl<HmlrContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == HmlrState.RUNNING }
    handle {
        edRepoPrepare = edValidated.deepCopy()
        // TODO будет реализовано в занятии по управлению пользователями
        edRepoPrepare.ownerId = HmlrEdStub.get().ownerId
    }
}
