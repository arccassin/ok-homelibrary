package com.otus.otuskotlin.homelibrary.api.v1.mappers

import com.otus.otuskotlin.homelibrary.api.v1.models.*
import com.otus.otuskotlin.homelibrary.common.HmlrContext
import com.otus.otuskotlin.homelibrary.common.models.*
import com.otus.otuskotlin.homelibrary.common.models.HmlrWorkMode
import com.otus.otuskotlin.homelibrary.common.stubs.HmlrStubs

fun HmlrContext.fromTransport(request: IRequest) = when (request) {
    is EdCreateRequest -> fromTransport(request)
    is EdReadRequest -> fromTransport(request)
    is EdUpdateRequest -> fromTransport(request)
    is EdDeleteRequest -> fromTransport(request)
    is EdSearchRequest -> fromTransport(request)
}

private fun String?.toEdId() = this?.let { HmlrEdId(it) } ?: HmlrEdId.NONE
private fun String?.toEdLock() = this?.let { HmlrEdLock(it) } ?: HmlrEdLock.NONE
private fun String?.toTitle() = this?.let { HmlrEdTitle(it) } ?: HmlrEdTitle.NONE
private fun String?.toAuthor() = this?.let { HmlrEdAuthor(it) } ?: HmlrEdAuthor.NONE
private fun String?.toIsbn() = this?.let { HmlrEdIsbn(it) } ?: HmlrEdIsbn.NONE

private fun EdReadObject?.toInternal() = if (this != null) {
    HmlrEd(id = id.toEdId())
} else {
    HmlrEd()
}

private fun EdDebug?.transportToWorkMode(): HmlrWorkMode = when (this?.mode) {
    EdRequestDebugMode.PROD -> HmlrWorkMode.PROD
    EdRequestDebugMode.TEST -> HmlrWorkMode.TEST
    EdRequestDebugMode.STUB -> HmlrWorkMode.STUB
    null -> HmlrWorkMode.PROD
}

private fun EdDebug?.transportToStubCase(): HmlrStubs = when (this?.stub) {
    EdRequestDebugStubs.SUCCESS -> HmlrStubs.SUCCESS
    EdRequestDebugStubs.NOT_FOUND -> HmlrStubs.NOT_FOUND
    EdRequestDebugStubs.BAD_ID -> HmlrStubs.BAD_ID
    EdRequestDebugStubs.BAD_TITLE -> HmlrStubs.BAD_TITLE
    EdRequestDebugStubs.BAD_DESCRIPTION -> HmlrStubs.BAD_DESCRIPTION
    EdRequestDebugStubs.BAD_VISIBILITY -> HmlrStubs.BAD_VISIBILITY
    EdRequestDebugStubs.CANNOT_DELETE -> HmlrStubs.CANNOT_DELETE
    EdRequestDebugStubs.BAD_SEARCH_STRING -> HmlrStubs.BAD_SEARCH_STRING
    null -> HmlrStubs.NONE
}

fun HmlrContext.fromTransport(request: EdCreateRequest) {
    command = HmlrCommand.CREATE
    edRequest = request.ed?.toInternal() ?: HmlrEd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun HmlrContext.fromTransport(request: EdReadRequest) {
    command = HmlrCommand.READ
    edRequest = request.ed.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun HmlrContext.fromTransport(request: EdUpdateRequest) {
    command = HmlrCommand.UPDATE
    edRequest = request.ed?.toInternal() ?: HmlrEd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun HmlrContext.fromTransport(request: EdDeleteRequest) {
    command = HmlrCommand.DELETE
    edRequest = request.ed.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun EdDeleteObject?.toInternal(): HmlrEd = if (this != null) {
    HmlrEd(
        id = id.toEdId(),
        lock = lock.toEdLock(),
    )
} else {
    HmlrEd()
}

fun HmlrContext.fromTransport(request: EdSearchRequest) {
    command = HmlrCommand.SEARCH
    edFilterRequest = request.edFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun EdSearchFilter?.toInternal(): HmlrEdFilter = HmlrEdFilter(
    searchString = this?.searchString ?: "",
    ownerId = this?.ownerId?.let { HmlrUserId(it) } ?: HmlrUserId.NONE,
    title = this?.title?.let { HmlrEdTitle(it)} ?: HmlrEdTitle.NONE,
    author = this?.author?.let { HmlrEdAuthor(it)} ?: HmlrEdAuthor.NONE,
    isbn = this?.isbn?.let { HmlrEdIsbn(it)} ?: HmlrEdIsbn.NONE,
)

private fun EdCreateObject.toInternal(): HmlrEd = HmlrEd(
    title = this.title.toTitle(),
    author = this.author.toAuthor(),
    isbn = this.isbn.toIsbn(),
    year = this.year ?: "",
)

private fun EdUpdateObject.toInternal(): HmlrEd = HmlrEd(
    id = this.id.toEdId(),
    title = this.title.toTitle(),
    author = this.author.toAuthor(),
    isbn = this.isbn.toIsbn(),
    year = this.year ?: "",
    lock = this.lock.toEdLock(),
)
