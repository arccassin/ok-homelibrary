package com.otus.otuskotlin.homelibrary.common.repo.exceptions

import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId

class RepoEmptyLockException(id: HmlrEdId): RepoEdException(
    id,
    "Lock is empty in DB"
)
