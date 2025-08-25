package com.otus.otuskotlin.homelibrary.repo.common

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.repo.IRepoEd

interface IRepoEdInitializable: IRepoEd {
    fun save(eds: Collection<HmlrEd>) : Collection<HmlrEd>
}
