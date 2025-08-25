package com.otus.otuskotlin.homelibrary.repo.common

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd

/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class EdRepoInitialized(
    val repo: IRepoEdInitializable,
    initObjects: Collection<HmlrEd> = emptyList(),
) : IRepoEdInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<HmlrEd> = save(initObjects).toList()
}