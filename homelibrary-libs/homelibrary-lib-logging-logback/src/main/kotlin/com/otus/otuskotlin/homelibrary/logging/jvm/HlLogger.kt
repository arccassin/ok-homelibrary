package com.otus.otuskotlin.homelibrary.logging.jvm

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import com.otus.otuskotlin.homelibrary.logging.common.IHlLogWrapper
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun hlLoggerLogback(logger: Logger): IHlLogWrapper = HlLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun hlLoggerLogback(clazz: KClass<*>): IHlLogWrapper = hlLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun hlLoggerLogback(loggerId: String): IHlLogWrapper = hlLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
