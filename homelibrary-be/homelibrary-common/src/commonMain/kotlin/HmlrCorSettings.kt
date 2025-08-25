package com.otus.otuskotlin.homelibrary.common

import com.otus.otuskotlin.homelibrary.common.repo.IRepoEd
import com.otus.otuskotlin.homelibrary.logging.common.HlLoggerProvider

data class HmlrCorSettings(
    val loggerProvider: HlLoggerProvider = HlLoggerProvider(),
    val repoStub: IRepoEd = IRepoEd.NONE,
    val repoTest: IRepoEd = IRepoEd.NONE,
    val repoProd: IRepoEd = IRepoEd.NONE,
) {
    companion object {
        val NONE = HmlrCorSettings()
    }
}