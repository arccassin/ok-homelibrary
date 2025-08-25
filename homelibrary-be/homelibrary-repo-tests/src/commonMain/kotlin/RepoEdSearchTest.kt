package com.otus.otuskotlin.homelibrary.backend.repo.tests

import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.common.models.HmlrUserId
import com.otus.otuskotlin.homelibrary.common.repo.DbEdFilterRequest
import com.otus.otuskotlin.homelibrary.common.repo.DbEdsResponseErr
import com.otus.otuskotlin.homelibrary.common.repo.DbEdsResponseOk
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.fail


abstract class RepoEdSearchTest {
    abstract val repo: EdRepoInitialized

    protected open val initializedObjects: List<HmlrEd> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchEd(DbEdFilterRequest(ownerId = searchOwnerId))
        if (result is DbEdsResponseErr) {
            result.errors.forEach { it.exception?.printStackTrace() }
            fail()
        }
        assertIs<DbEdsResponseOk>(result)
        val expected = listOf(initializedObjects[1], initializedObjects[2]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object: BaseInitEds("search") {

        val searchOwnerId = HmlrUserId("owner-124")
        override val initObjects: List<HmlrEd> = listOf(
            createInitTestModel("ed1"),
            createInitTestModel("ed2", ownerId = searchOwnerId),
            createInitTestModel("ed4", ownerId = searchOwnerId),
        )
    }
}
