package com.otus.otuskotlin.homelibrary.stubs

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdAuthor
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdTitle
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStubBook.ED_Edition_BOOK1

object HmlrEdStub {
    fun get(): HmlrEd = ED_Edition_BOOK1.copy()

    fun prepareResult(block: HmlrEd.() -> Unit): HmlrEd = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        hmlrEdEdition("d-666-01", filter),
        hmlrEdEdition("d-666-02", filter),
        hmlrEdEdition("d-666-03", filter),
        hmlrEdEdition("d-666-04", filter),
        hmlrEdEdition("d-666-05", filter),
        hmlrEdEdition("d-666-06", filter),
    )

    private fun hmlrEdEdition(id: String, filter: String) =
        hmlrEd(ED_Edition_BOOK1, id = id, filter = filter)

    private fun hmlrEd(base: HmlrEd, id: String, filter: String) = base.copy(
        id = HmlrEdId(id),
        title = HmlrEdTitle("$filter $id"),
        author = HmlrEdAuthor("auth $filter $id"),
    )

}
