package com.otus.otuskotlin.homelibrary.backend.repo.tests

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.repo.*

class EdRepositoryMock(
    private val invokeCreateEd: (DbEdRequest) -> IDbEdResponse = { DEFAULT_ED_SUCCESS_EMPTY_MOCK },
    private val invokeReadEd: (DbEdIdRequest) -> IDbEdResponse = { DEFAULT_ED_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateEd: (DbEdRequest) -> IDbEdResponse = { DEFAULT_ED_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteEd: (DbEdIdRequest) -> IDbEdResponse = { DEFAULT_ED_SUCCESS_EMPTY_MOCK },
    private val invokeSearchEd: (DbEdFilterRequest) -> IDbEdsResponse = { DEFAULT_EDS_SUCCESS_EMPTY_MOCK },
): IRepoEd {
    override suspend fun createEd(rq: DbEdRequest): IDbEdResponse {
        return invokeCreateEd(rq)
    }

    override suspend fun readEd(rq: DbEdIdRequest): IDbEdResponse {
        return invokeReadEd(rq)
    }

    override suspend fun updateEd(rq: DbEdRequest): IDbEdResponse {
        return invokeUpdateEd(rq)
    }

    override suspend fun deleteEd(rq: DbEdIdRequest): IDbEdResponse {
        return invokeDeleteEd(rq)
    }

    override suspend fun searchEd(rq: DbEdFilterRequest): IDbEdsResponse {
        return invokeSearchEd(rq)
    }

    companion object {
        val DEFAULT_ED_SUCCESS_EMPTY_MOCK = DbEdResponseOk(HmlrEd())
        val DEFAULT_EDS_SUCCESS_EMPTY_MOCK = DbEdsResponseOk(emptyList())
    }
}
