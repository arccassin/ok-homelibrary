package com.otus.otuskotlin.homelibrary.biz

import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub

@Suppress("unused", "RedundantSuspendModifier")
class HmlrEdProcessor(val corSettings: HmlrCorSettings) {

    suspend fun exec(ctx: HmlrContext) {
        ctx.edResponse = HmlrEdStub.get()
        ctx.edsResponse = HmlrEdStub.prepareSearchList("ed search").toMutableList()
        ctx.state = HmlrState.RUNNING
    }
}