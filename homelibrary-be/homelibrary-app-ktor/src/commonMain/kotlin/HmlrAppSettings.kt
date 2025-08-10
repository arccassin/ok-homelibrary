package com.otus.otuskotlin.homelibrary.app.ktor

import com.otus.otuskotlin.homelibrary.app.common.IHmlrAppSettings
import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings

data class HmlrAppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: HmlrCorSettings = HmlrCorSettings(),
    override val processor: HmlrEdProcessor = HmlrEdProcessor(corSettings),
): IHmlrAppSettings
