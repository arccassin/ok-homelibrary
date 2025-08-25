package com.otus.otuskotlin.homelibrary.common.repo

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrError

sealed interface IDbEdResponse: IDbResponse<HmlrEd>

data class DbEdResponseOk(
    val data: HmlrEd
): IDbEdResponse

data class DbEdResponseErr(
    val errors: List<HmlrError> = emptyList()
): IDbEdResponse {
    constructor(err: HmlrError): this(listOf(err))
}

data class DbEdResponseErrWithData(
    val data: HmlrEd,
    val errors: List<HmlrError> = emptyList()
): IDbEdResponse {
    constructor(ed: HmlrEd, err: HmlrError): this(ed, listOf(err))
}
