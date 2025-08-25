package com.otus.otuskotlin.homelibrary.common.repo.exceptions

import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId

open class RepoEdException(
    @Suppress("unused")
    val adId: HmlrEdId,
    msg: String,
): RepoException(msg)
