package com.otus.otuskotlin.homelibrary.stubs

import com.otus.otuskotlin.homelibrary.common.models.*

object HmlrEdStubBook {
    val ED_Edition_BOOK1: HmlrEd
        get() = HmlrEd(
            id = HmlrEdId("666"),
            title = HmlrEdTitle("Бесы"),
            author = HmlrEdAuthor("Федор Достоевский"),
            isbn = HmlrEdIsbn("2312312123123"),
            year = "1984",
            ownerId = HmlrUserId("user-1"),
            lock = HmlrEdLock("123"),
            permissionsClient = mutableSetOf(
                HmlrEdPermissionClient.READ,
                HmlrEdPermissionClient.UPDATE,
                HmlrEdPermissionClient.DELETE,
                HmlrEdPermissionClient.MAKE_VISIBLE_PUBLIC,
                HmlrEdPermissionClient.MAKE_VISIBLE_GROUP,
                HmlrEdPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
}
