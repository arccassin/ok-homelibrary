package com.otus.otuskotlin.homelibrary.repo.inmemory

import com.otus.otuskotlin.homelibrary.common.models.*

data class EdEntity(
    val id: String? = null,
    val title: String? = null,
    val author: String? = null,
    val isbn: String? = null,
    val year: String? = null,
    val ownerId: String? = null,
    val adType: String? = null,
    val visibility: String? = null,
    val lock: String? = null,
) {
    constructor(model: HmlrEd): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.asString().takeIf { it.isNotBlank() },
        author = model.author.asString().takeIf { it.isNotBlank() },
        isbn = model.isbn.asString().takeIf { it.isNotBlank() },
        year = model.year.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        lock = model.lock.asString().takeIf { it.isNotBlank() }
        // Не нужно сохранять permissions, потому что он ВЫЧИСЛЯЕМЫЙ, а не хранимый
    )

    fun toInternal() = HmlrEd(
        id = id?.let { HmlrEdId(it) }?: HmlrEdId.NONE,
        title = title?.let { HmlrEdTitle(it) } ?: HmlrEdTitle.NONE,
        author = author?.let { HmlrEdAuthor(it) } ?: HmlrEdAuthor.NONE,
        isbn = isbn?.let { HmlrEdIsbn(it) } ?: HmlrEdIsbn.NONE,
        year = year ?: "",
        ownerId = ownerId?.let { HmlrUserId(it) } ?: HmlrUserId.NONE,
        lock = lock?.let { HmlrEdLock(it) } ?: HmlrEdLock.NONE,
    )
}
