package com.otus.otuskotlin.homelibrary.biz.validation

import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand

abstract class BaseBizValidationTest {
    protected abstract val command: HmlrCommand
    private val settings by lazy { HmlrCorSettings() }
    protected val processor by lazy { HmlrEdProcessor(settings) }
}
