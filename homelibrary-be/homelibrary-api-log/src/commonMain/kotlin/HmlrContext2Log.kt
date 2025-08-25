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
        state = state.toLog(),
        requestId = requestId.takeIf { it != HmlrRequestId.NONE }?.asString(),
        requestEd = edRequest.takeIf { it != edNone }?.toLog(),
        validatedEd = edValidated.takeIf { it != edNone }?.toLog(),
        responseEd = edResponse.takeIf { it != edNone }?.toLog(),
        responseEds = edsResponse.takeIf { it.isNotEmpty() }?.filter { it != edNone }?.map { it.toLog() },
        repoReadEd = edRepoRead.takeIf { it != edNone }?.toLog(),
        repoPrepareEd = edRepoPrepare.takeIf { it != edNone }?.toLog(),
        repoDoneEd = edRepoDone.takeIf { it != edNone }?.toLog(),
        requestFilter = edFilterRequest.takeIf { it != HmlrEdFilter() }?.toLog(),
        validatedFilter = edFilterValidated.takeIf { it != HmlrEdFilter() }?.toLog(),
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

private fun HmlrState.toLog() : EdState = when(this) {
    HmlrState.NONE -> EdState.NONE
    HmlrState.RUNNING -> EdState.RUNNING
    HmlrState.FAILING -> EdState.FAILING
    HmlrState.FINISHING -> EdState.FINISHED
}