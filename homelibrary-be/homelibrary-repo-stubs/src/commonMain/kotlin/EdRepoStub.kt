package com.otus.otuskotlin.homelibrary.backend.repository.inmemory

import com.otus.otuskotlin.homelibrary.common.repo.*
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub

class EdRepoStub() : IRepoEd {
    override suspend fun createEd(rq: DbEdRequest): IDbEdResponse {
        return DbEdResponseOk(
            data = HmlrEdStub.get(),
        )
    }

    override suspend fun readEd(rq: DbEdIdRequest): IDbEdResponse {
        return DbEdResponseOk(
            data = HmlrEdStub.get(),
        )
    }

    override suspend fun updateEd(rq: DbEdRequest): IDbEdResponse {
        return DbEdResponseOk(
            data = HmlrEdStub.get(),
        )
    }

    override suspend fun deleteEd(rq: DbEdIdRequest): IDbEdResponse {
        return DbEdResponseOk(
            data = HmlrEdStub.get(),
        )
    }

    override suspend fun searchEd(rq: DbEdFilterRequest): IDbEdsResponse {
        return DbEdsResponseOk(
            data = HmlrEdStub.prepareSearchList(filter = ""),
        )
    }
}
