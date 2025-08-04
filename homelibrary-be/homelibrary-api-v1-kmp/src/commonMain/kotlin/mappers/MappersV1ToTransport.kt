package com.otus.otuskotlin.homelibrary.api.v1.mappers

import com.otus.otuskotlin.homelibrary.common.exceptions.UnknownHmlrCommand
import com.otus.otuskotlin.homelibrary.api.v1.models.*
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*

fun HmlrContext.toTransportEd(): IResponse = when (val cmd = command) {
    HmlrCommand.CREATE -> toTransportCreate()
    HmlrCommand.READ -> toTransportRead()
    HmlrCommand.UPDATE -> toTransportUpdate()
    HmlrCommand.DELETE -> toTransportDelete()
    HmlrCommand.SEARCH -> toTransportSearch()
    HmlrCommand.NONE -> throw UnknownHmlrCommand(cmd)
}

fun HmlrContext.toTransportCreate() = EdCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ed = edResponse.toTransportEd()
)

fun HmlrContext.toTransportRead() = EdReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ed = edResponse.toTransportEd()
)

fun HmlrContext.toTransportUpdate() = EdUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ed = edResponse.toTransportEd()
)

fun HmlrContext.toTransportDelete() = EdDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ed = edResponse.toTransportEd()
)

fun HmlrContext.toTransportSearch() = EdSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    eds = edsResponse.toTransportEd()
)

fun HmlrContext.toTransportInit() = EdInitResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
)

fun List<HmlrEd>.toTransportEd(): List<EdResponseObject>? = this
    .map { it.toTransportEd() }
    .toList()
    .takeIf { it.isNotEmpty() }

internal fun HmlrEd.toTransportEd(): EdResponseObject = EdResponseObject(
    id = id.toTransportEd(),
    title = title.toTransportEd(),
    author = author.toTransportEd(),
    isbn = isbn.toTransportEd(),
    year = year.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != HmlrUserId.NONE }?.asString(),
    permissions = permissionsClient.toTransportEd(),
    lock = lock.takeIf { it != HmlrEdLock.NONE }?.asString()
)

internal fun HmlrEdId.toTransportEd() = takeIf { it != HmlrEdId.NONE }?.asString()
internal fun HmlrEdTitle.toTransportEd() = takeIf { it != HmlrEdTitle.NONE }?.asString()
internal fun HmlrEdAuthor.toTransportEd() = takeIf { it != HmlrEdAuthor.NONE }?.asString()
internal fun HmlrEdIsbn.toTransportEd() = takeIf { it != HmlrEdIsbn.NONE }?.asString()

private fun Set<HmlrEdPermissionClient>.toTransportEd(): Set<EdPermissions>? = this
    .map { it.toTransportEd() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun HmlrEdPermissionClient.toTransportEd() = when (this) {
    HmlrEdPermissionClient.READ -> EdPermissions.READ
    HmlrEdPermissionClient.UPDATE -> EdPermissions.UPDATE
    HmlrEdPermissionClient.MAKE_VISIBLE_OWNER -> EdPermissions.MAKE_VISIBLE_OWN
    HmlrEdPermissionClient.MAKE_VISIBLE_GROUP -> EdPermissions.MAKE_VISIBLE_GROUP
    HmlrEdPermissionClient.MAKE_VISIBLE_PUBLIC -> EdPermissions.MAKE_VISIBLE_PUBLIC
    HmlrEdPermissionClient.DELETE -> EdPermissions.DELETE
}

private fun List<HmlrError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportEd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun HmlrError.toTransportEd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun HmlrState.toResult(): ResponseResult? = when (this) {
    HmlrState.RUNNING, HmlrState.FINISHING -> ResponseResult.SUCCESS
    HmlrState.FAILING -> ResponseResult.ERROR
    HmlrState.NONE -> null
}
