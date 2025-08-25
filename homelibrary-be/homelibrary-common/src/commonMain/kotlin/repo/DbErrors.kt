package com.otus.otuskotlin.homelibrary.common.repo

import com.otus.otuskotlin.homelibrary.common.helpers.errorSystem
import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdLock
import com.otus.otuskotlin.homelibrary.common.models.HmlrError
import com.otus.otuskotlin.homelibrary.common.repo.exceptions.RepoConcurrencyException
import com.otus.otuskotlin.homelibrary.common.repo.exceptions.RepoException

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: HmlrEdId) = DbEdResponseErr(
    HmlrError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbEdResponseErr(
    HmlrError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorRepoConcurrency(
    oldEd: HmlrEd,
    expectedLock: HmlrEdLock,
    exception: Exception = RepoConcurrencyException(
        id = oldEd.id,
        expectedLock = expectedLock,
        actualLock = oldEd.lock,
    ),
) = DbEdResponseErrWithData(
    ed = oldEd,
    err = HmlrError(
        code = "$ERROR_GROUP_REPO-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "The object with ID ${oldEd.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    )
)

fun errorEmptyLock(id: HmlrEdId) = DbEdResponseErr(
    HmlrError(
        code = "$ERROR_GROUP_REPO-lock-empty",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "Lock for Ad ${id.asString()} is empty that is not admitted"
    )
)

fun errorDb(e: RepoException) = DbEdResponseErr(
    errorSystem(
        violationCode = "db-error",
        e = e
    )
)
