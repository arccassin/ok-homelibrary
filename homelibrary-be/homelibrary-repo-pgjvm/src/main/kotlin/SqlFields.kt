package com.otus.otuskotlin.homelibrary.backend.repo.postgresql

object SqlFields {
    const val ID = "id"
    const val TITLE = "title"
    const val AUTHOR = "author"
    const val ISBN = "isbn"
    const val YEAR = "year"
    const val LOCK = "lock"
    const val LOCK_OLD = "lock_old"
    const val OWNER_ID = "owner_id"

    const val FILTER_TITLE = TITLE
    const val FILTER_OWNER_ID = OWNER_ID

    const val DELETE_OK = "DELETE_OK"

    fun String.quoted() = "\"$this\""
    val allFields = listOf(
        ID, TITLE, AUTHOR, ISBN, YEAR, LOCK, OWNER_ID,
    )
}
