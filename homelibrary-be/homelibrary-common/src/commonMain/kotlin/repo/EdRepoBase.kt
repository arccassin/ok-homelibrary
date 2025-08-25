package com.otus.otuskotlin.homelibrary.common.repo

import com.otus.otuskotlin.homelibrary.common.helpers.errorSystem

abstract class EdRepoBase: IRepoEd {

    protected suspend fun tryEdMethod(block: suspend () -> IDbEdResponse) = try {
        block()
    } catch (e: Throwable) {
        DbEdResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryEdsMethod(block: suspend () -> IDbEdsResponse) = try {
        block()
    } catch (e: Throwable) {
        DbEdsResponseErr(errorSystem("methodException", e = e))
    }

}