package com.otus.otuskotlin.homelibrary.common.repo.exceptions

import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdLock

class RepoConcurrencyException(id: HmlrEdId, expectedLock: HmlrEdLock, actualLock: HmlrEdLock?): RepoEdException(
    id,
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
