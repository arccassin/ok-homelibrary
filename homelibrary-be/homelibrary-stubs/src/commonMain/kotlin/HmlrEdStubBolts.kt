package com.otus.otuskotlin.homelibrary.stubs

import com.otus.otuskotlin.homelibrary.common.models.*

object HmlrEdStubBolts {
    val AD_DEMAND_BOLT1: HmlrEd
        get() = HmlrEd(
            id = HmlrEdId("666"),
            title = HmlrEdTitle("Преступление и наказание"),
            author = HmlrEdAuthor("Достоевский Федор Михайлович"),
            isbn = HmlrEdIsbn("9785961490282"),
            year = "2023",
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
