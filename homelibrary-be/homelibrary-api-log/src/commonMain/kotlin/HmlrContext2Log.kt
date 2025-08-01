package com.otus.otuskotlin.homelibrary.api.log.mapper

import kotlinx.datetime.Clock
import com.otus.otuskotlin.homelibrary.api.log.models.*
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import kotlin.takeIf

fun HmlrContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "homelibrary",
    ed = toHmlrLog(),
    errors = errors.map { it.toLog() },
)

private fun HmlrContext.toHmlrLog(): HmlrLogModel? {
    val edNone = HmlrEd()
    return HmlrLogModel(
        requestId = requestId.takeIf { it != HmlrRequestId.NONE }?.asString(),
        requestEd = edRequest.takeIf { it != edNone }?.toLog(),
        responseEd = edResponse.takeIf { it != edNone }?.toLog(),
        responseEds = edsResponse.takeIf { it.isNotEmpty() }?.filter { it != edNone }?.map { it.toLog() },
        requestFilter = edFilterRequest.takeIf { it != HmlrEdFilter() }?.toLog(),
    ).takeIf { it != HmlrLogModel() }
}

private fun HmlrEdFilter.toLog() = EdFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    )

private fun HmlrError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

private fun HmlrEd.toLog() = EdLog(
    id = id.takeIf { it != HmlrEdId.NONE }?.asString(),
    title = title.takeIf { it != HmlrEdTitle.NONE }?.asString(),
    author = author.takeIf { it != HmlrEdAuthor.NONE }?.asString(),
    isbn = isbn.takeIf { it != HmlrEdIsbn.NONE }?.asString(),
    year = year.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != HmlrUserId.NONE }?.asString(),
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)