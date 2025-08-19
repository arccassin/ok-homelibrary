package com.otus.otuskotlin.homelibrary.biz.general

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.common.models.HmlrWorkMode
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.chain

fun ICorChainDsl<HmlrContext>.stubs(title: String, block: ICorChainDsl<HmlrContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == HmlrWorkMode.STUB && state == HmlrState.RUNNING }
}
