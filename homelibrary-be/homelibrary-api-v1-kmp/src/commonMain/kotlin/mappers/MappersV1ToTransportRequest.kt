package com.otus.otuskotlin.homelibrary.api.v1.mappers

import com.otus.otuskotlin.homelibrary.api.v1.models.EdCreateObject
import com.otus.otuskotlin.homelibrary.api.v1.models.EdDeleteObject
import com.otus.otuskotlin.homelibrary.api.v1.models.EdReadObject
import com.otus.otuskotlin.homelibrary.api.v1.models.EdUpdateObject
import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdLock

fun HmlrEd.toTransportCreateAd() = EdCreateObject(
    title = title.toTransportEd(),
    author = author.toTransportEd(),
    isbn = isbn.toTransportEd(),
    year = year,
    )

fun HmlrEd.toTransportReadAd() = EdReadObject(
    id = id.toTransportEd()
)

fun HmlrEd.toTransportUpdateAd() = EdUpdateObject(
    id = id.toTransportEd(),
    title = title.toTransportEd(),
    author = author.toTransportEd(),
    isbn = isbn.toTransportEd(),
    year = year,
    lock = lock.toTransportEd(),
)

internal fun HmlrEdLock.toTransportEd() = takeIf { it != HmlrEdLock.NONE }?.asString()

fun HmlrEd.toTransportDeleteAd() = EdDeleteObject(
    id = id.toTransportEd(),
    lock = lock.toTransportEd(),
)