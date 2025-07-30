package com.otus.otuskotlin.homelibrary.common.models

data class HmlrEdFilter(
    var searchString: String = "",
    var ownerId: HmlrUserId = HmlrUserId.NONE,
    var title: HmlrEdTitle = HmlrEdTitle.NONE,
    var author: HmlrEdAuthor = HmlrEdAuthor.NONE,
    var isbn: HmlrEdIsbn = HmlrEdIsbn.NONE,
)
