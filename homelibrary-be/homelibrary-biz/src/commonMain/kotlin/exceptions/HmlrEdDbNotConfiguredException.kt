package com.otus.otuskotlin.homelibrary.biz.exceptions

import com.otus.otuskotlin.homelibrary.common.models.HmlrWorkMode

class HmlrEdDbNotConfiguredException(val workMode: HmlrWorkMode): Exception(
    "Database is not configured properly for workmode $workMode"
)
