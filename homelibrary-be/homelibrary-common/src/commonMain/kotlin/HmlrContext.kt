package com.otus.otuskotlin.homelibrary.common

import kotlinx.datetime.Instant
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.repo.IRepoEd
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs
import com.otus.otuskotlin.homelibrary.logging.common.IHlLogWrapper

data class HmlrContext(
    var corSettings: HmlrCorSettings = HmlrCorSettings(),
    var logger: IHlLogWrapper = corSettings.loggerProvider.logger("HmlrContext"),

    var command: HmlrCommand = HmlrCommand.NONE,
    var state: HmlrState = HmlrState.NONE,
    val errors: MutableList<HmlrError> = mutableListOf(),

    var workMode: HmlrWorkMode = HmlrWorkMode.PROD,
    var stubCase: HmlrStubs = HmlrStubs.NONE,

    var requestId: HmlrRequestId = HmlrRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var edRequest: HmlrEd = HmlrEd(),

    var edResponse: HmlrEd = HmlrEd(),
    var edsResponse: MutableList<HmlrEd> = mutableListOf(),
    var edValidated: HmlrEd = HmlrEd(),

    var edFilterRequest: HmlrEdFilter = HmlrEdFilter(),
    var edFilterValidated: HmlrEdFilter = HmlrEdFilter(),

    var edRepo: IRepoEd = IRepoEd.NONE,
    var edRepoRead: HmlrEd = HmlrEd(), // То, что прочитали из репозитория
    var edRepoPrepare: HmlrEd = HmlrEd(), // То, что готовим для сохранения в БД
    var edRepoDone: HmlrEd = HmlrEd(),  // Результат, полученный из БД
    var edsRepoDone: MutableList<HmlrEd> = mutableListOf(),
    )
