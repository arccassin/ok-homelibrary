package com.otus.otuskotlin.homelibrary.app.common

import kotlinx.datetime.Clock
import com.otus.otuskotlin.homelibrary.api.log.mapper.toLog
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.helpers.asHmlrError
import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand
import com.otus.otuskotlin.homelibrary.common.models.HmlrState
import kotlin.reflect.KClass

suspend inline fun <T> IHmlrAppSettings.controllerHelper(
    crossinline getRequest: suspend HmlrContext.() -> Unit,
    crossinline toResponse: suspend HmlrContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = HmlrContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.state = HmlrState.FAILING
        ctx.errors.add(e.asHmlrError())
        processor.exec(ctx)
        if (ctx.command == HmlrCommand.NONE) {
            ctx.command = HmlrCommand.READ
        }
        ctx.toResponse()
    }
}
