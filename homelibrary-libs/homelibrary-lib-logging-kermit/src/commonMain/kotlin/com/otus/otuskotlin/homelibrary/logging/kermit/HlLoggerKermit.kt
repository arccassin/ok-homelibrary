package com.otus.otuskotlin.homelibrary.logging.kermit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import com.otus.otuskotlin.homelibrary.logging.common.IHlLogWrapper
import kotlin.reflect.KClass

@Suppress("unused")
fun hlLoggerKermit(loggerId: String): IHlLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return HlLoggerWrapperKermit(
        logger = logger,
        loggerId = loggerId,
    )
}

@Suppress("unused")
fun gbrLoggerKermit(cls: KClass<*>): IHlLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return HlLoggerWrapperKermit(
        logger = logger,
        loggerId = cls.qualifiedName ?: "",
    )
}
