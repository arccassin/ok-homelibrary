package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.api.log.mapper.toLog
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.repo.DbEdIdRequest
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErr
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseErrWithData
import com.otus.otuskotlin.homelibrary.common.repo.DbEdResponseOk
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker
import kotlin.toString

fun ICorChainDsl<HmlrContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение издания из БД"
    on { state == HmlrState.RUNNING }
    handle {
        logger.debug(
            msg = "Чтение издания из БД по ID started",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
        val request = DbEdIdRequest(edValidated)
        when(val result = edRepo.readEd(request)) {
            is DbEdResponseOk -> edRepoRead = result.data
            is DbEdResponseErr -> fail(result.errors)
            is DbEdResponseErrWithData -> {
                fail(result.errors)
                edRepoRead = result.data
            }
        }
        logger.debug(
            msg = "Чтение издания из БД по ID finished",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
    }
}
