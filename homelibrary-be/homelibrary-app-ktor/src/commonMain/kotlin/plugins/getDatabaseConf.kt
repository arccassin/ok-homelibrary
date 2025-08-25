package com.otus.otuskotlin.homelibrary.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.homelibrary.repo.inmemory.EdRepoInMemory
import com.otus.otuskotlin.homelibrary.common.repo.IRepoEd
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

expect fun Application.getDatabaseConf(type: EdDbType): IRepoEd

enum class EdDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

fun Application.initInMemory(): IRepoEd {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return EdRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}
