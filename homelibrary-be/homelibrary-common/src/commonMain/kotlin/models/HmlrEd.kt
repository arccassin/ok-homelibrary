package com.otus.otuskotlin.homelibrary.common.models

data class HmlrEd(
    var id: HmlrEdId = HmlrEdId.NONE,

    var title: HmlrEdTitle = HmlrEdTitle.NONE,
    var author: HmlrEdAuthor = HmlrEdAuthor.NONE,
    var isbn: HmlrEdIsbn = HmlrEdIsbn.NONE,
    var year: String = "",

    var ownerId: HmlrUserId = HmlrUserId.NONE,
    var lock: HmlrEdLock = HmlrEdLock.NONE,
    val permissionsClient: MutableSet<HmlrEdPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): HmlrEd = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = HmlrEd()
    }

}
