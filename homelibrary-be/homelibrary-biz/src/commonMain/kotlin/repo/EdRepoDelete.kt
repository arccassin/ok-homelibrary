package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.repo.DbEdIdRequest
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErr
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErrWithData
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == HmlrState.RUNNING }
    handle {
        val request = DbEdIdRequest(edRepoPrepare)
        when(val result = edRepo.deleteEd(request)) {
            is DbEdResponseOk -> edRepoDone = result.data
            is DbEdResponseErr -> {
                fail(result.errors)
                edRepoDone = edRepoRead
            }
            is DbEdResponseErrWithData -> {
                fail(result.errors)
                edRepoDone = result.data
            }
        }
    }
}
