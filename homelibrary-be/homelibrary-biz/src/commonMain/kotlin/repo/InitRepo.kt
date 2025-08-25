package com.otus.otuskotlin.homelibrary.biz.repo

import com.otus.otuskotlin.homelibrary.biz.exceptions.HmlrEdDbNotConfiguredException
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.errorSystem
import com.otus.otuskotlin.homelibrary.common.helpers.fail
import com.otus.otuskotlin.homelibrary.common.models.HmlrWorkMode
import com.otus.otuskotlin.homelibrary.common.repo.IRepoEd
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.worker

fun ICorChainDsl<HmlrContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        edRepo = when {
            workMode == HmlrWorkMode.TEST -> corSettings.repoTest
            workMode == HmlrWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != HmlrWorkMode.STUB && edRepo == IRepoEd.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = HmlrEdDbNotConfiguredException(workMode)
            )
        )
    }
}
