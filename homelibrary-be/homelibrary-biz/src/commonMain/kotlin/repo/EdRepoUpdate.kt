package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.repo.DbEdRequest
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErr
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErrWithData
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == HmlrState.RUNNING }
    handle {
        val request = DbEdRequest(edRepoPrepare)
        when(val result = edRepo.updateEd(request)) {
            is DbEdResponseOk -> edRepoDone = result.data
            is DbEdResponseErr -> fail(result.errors)
            is DbEdResponseErrWithData -> {
                fail(result.errors)
                edRepoDone = result.data
            }
        }
    }
}
