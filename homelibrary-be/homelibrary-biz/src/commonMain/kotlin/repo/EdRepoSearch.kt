package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.repo.DbEdFilterRequest
import com.otus.otuskotlin.homelibrary.common.repo.DbEdsResponseErr
import com.otus.otuskotlin.homelibrary.common.repo.DbEdsResponseOk
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == HmlrState.RUNNING }
    handle {
        val request = DbEdFilterRequest(
            titleFilter = edFilterValidated.searchString,
            ownerId = edFilterValidated.ownerId,
        )
        when(val result = edRepo.searchEd(request)) {
            is DbEdsResponseOk -> edsRepoDone = result.data.toMutableList()
            is DbEdsResponseErr -> fail(result.errors)
        }
    }
}
