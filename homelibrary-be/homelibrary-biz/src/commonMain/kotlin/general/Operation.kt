package com.otus.otuskotlin.homelibrary.biz.general

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.cor.ICorChainDsl
import com.otus.otuskotlin.homelibrary.cor.chain

fun ICorChainDsl<HmlrContext>.operation(
    title: String,
    command: HmlrCommand,
    block: ICorChainDsl<HmlrContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == HmlrState.RUNNING }
}
