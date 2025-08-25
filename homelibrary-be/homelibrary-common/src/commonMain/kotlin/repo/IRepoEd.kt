package com.otus.otuskotlin.homelibrary.common.repo

interface IRepoEd {
    suspend fun createEd(rq: DbEdRequest): IDbEdResponse
    suspend fun readEd(rq: DbEdIdRequest): IDbEdResponse
    suspend fun updateEd(rq: DbEdRequest): IDbEdResponse
    suspend fun deleteEd(rq: DbEdIdRequest): IDbEdResponse
    suspend fun searchEd(rq: DbEdFilterRequest): IDbEdsResponse
    companion object {
        val NONE = object : IRepoEd {
            override suspend fun createEd(rq: DbEdRequest): IDbEdResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readEd(rq: DbEdIdRequest): IDbEdResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateEd(rq: DbEdRequest): IDbEdResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteEd(rq: DbEdIdRequest): IDbEdResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchEd(rq: DbEdFilterRequest): IDbEdsResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}
