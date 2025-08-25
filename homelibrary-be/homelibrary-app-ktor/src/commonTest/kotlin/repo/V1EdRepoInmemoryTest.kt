package com.otus.otuskotlin.homelibrary.app.ktor.repo

import com.otus.otuskotlin.homelibrary.api.v1.models.EdRequestDebugMode
import com.otus.otuskotlin.homelibrary.app.ktor.HmlrAppSettings
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.repo.IRepoEd
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import com.otus.otuskotlin.homelibrary.repo.inmemory.EdRepoInMemory

class V1EdRepoInmemoryTest : V1EdRepoBaseTest() {
    override val workMode: EdRequestDebugMode = EdRequestDebugMode.TEST
    private fun mkAppSettings(repo: IRepoEd) = HmlrAppSettings(
        corSettings = HmlrCorSettings(
            repoTest = repo
        )
    )

    override val appSettingsCreate: HmlrAppSettings = mkAppSettings(
        repo = EdRepoInitialized(EdRepoInMemory(randomUuid = { uuidNew }))
    )
    override val appSettingsRead: HmlrAppSettings = mkAppSettings(
        repo = EdRepoInitialized(
            EdRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initEd),
        )
    )
    override val appSettingsUpdate: HmlrAppSettings = mkAppSettings(
        repo = EdRepoInitialized(
            EdRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initEd),
        )
    )
    override val appSettingsDelete: HmlrAppSettings = mkAppSettings(
        repo = EdRepoInitialized(
            EdRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initEd),
        )
    )
    override val appSettingsSearch: HmlrAppSettings = mkAppSettings(
        repo = EdRepoInitialized(
            EdRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initEd),
        )
    )
}
