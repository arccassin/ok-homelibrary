package com.otus.otuskotlin.homelibrary.common.repo

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrError

sealed interface IDbEdsResponse: IDbResponse<List<HmlrEd>>

data class DbEdsResponseOk(
    val data: List<HmlrEd>
): IDbEdsResponse

@Suppress("unused")
data class DbEdsResponseErr(
    val errors: List<HmlrError> = emptyList()
): IDbEdsResponse {
    constructor(err: HmlrError): this(listOf(err))
}
