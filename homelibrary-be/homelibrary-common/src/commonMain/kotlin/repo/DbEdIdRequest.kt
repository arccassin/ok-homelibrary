package com.otus.otuskotlin.homelibrary.common.repo

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdLock

data class DbEdIdRequest(
    val id: HmlrEdId,
    val lock: HmlrEdLock = HmlrEdLock.NONE,
) {
    constructor(ed: HmlrEd): this(ed.id, ed.lock)
}
