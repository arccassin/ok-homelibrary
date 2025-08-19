package com.otus.otuskotlin.homelibrary.common.models

data class HmlrEdFilter(
    var searchString: String = "",
    var ownerId: HmlrUserId = HmlrUserId.NONE,
    var title: HmlrEdTitle = HmlrEdTitle.NONE,
    var author: HmlrEdAuthor = HmlrEdAuthor.NONE,
    var isbn: HmlrEdIsbn = HmlrEdIsbn.NONE,
) {
    fun deepCopy(): HmlrEdFilter = copy()

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = HmlrEdFilter()
    }
}
