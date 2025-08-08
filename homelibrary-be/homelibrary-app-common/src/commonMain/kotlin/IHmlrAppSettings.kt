package com.otus.otuskotlin.homelibrary.app.common

import com.otus.otuskotlin.homelibrary.biz.HmlrEdProcessor
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings

interface IHmlrAppSettings {
    val processor: HmlrEdProcessor
    val corSettings: HmlrCorSettings
}
