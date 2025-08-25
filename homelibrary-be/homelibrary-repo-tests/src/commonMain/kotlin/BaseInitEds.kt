package com.otus.otuskotlin.homelibrary.backend.repo.tests

import com.otus.otuskotlin.homelibrary.common.models.*

abstract class BaseInitEds(private val op: String): IInitObjects<HmlrEd> {
    open val lockOld: HmlrEdLock = HmlrEdLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: HmlrEdLock = HmlrEdLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: HmlrUserId = HmlrUserId("owner-123"),
        lock: HmlrEdLock = lockOld,
    ) = HmlrEd(
        id = HmlrEdId("ed-repo-$op-$suf"),
        title = HmlrEdTitle("$suf stub"),
        author = HmlrEdAuthor("$suf stub author"),
        isbn = HmlrEdIsbn("$suf stub isbn"),
        year = "198${suf.last()}",
        ownerId = ownerId,
        lock = lock,
    )
}
