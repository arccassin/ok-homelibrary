package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand
import com.otus.otuskotlin.homelibrary.common.models.HmlrEdId
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import com.otus.otuskotlin.homelibrary.repo.inmemory.EdRepoInMemory
import com.otus.otuskotlin.homelibrary.stubs.HmlrEdStub

abstract class BaseBizValidationTest {
    protected abstract val command: HmlrCommand
    private val repo = EdRepoInitialized(
        repo = EdRepoInMemory(),
        initObjects = listOf(
            HmlrEdStub.get(),
            HmlrEdStub.get().apply {
                id = HmlrEdId("123-234-abc-ABC")
            }
        ),
    )
    private val settings by lazy { HmlrCorSettings(repoTest = repo) }
    protected val processor by lazy { HmlrEdProcessor(settings) }
}
