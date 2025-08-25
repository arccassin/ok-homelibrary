package com.otus.otuskotlin.homelibrary.common.repo

import com.otus.otuskotlin.homelibrary.common.models.HmlrUserId

data class DbEdFilterRequest(
    val titleFilter: String = "",
    val ownerId: HmlrUserId = HmlrUserId.NONE,
)
