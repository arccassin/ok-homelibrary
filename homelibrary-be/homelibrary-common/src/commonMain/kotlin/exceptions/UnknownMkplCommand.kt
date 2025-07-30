package com.otus.otuskotlin.homelibrary.common.exceptions

import com.otus.otuskotlin.homelibrary.common.models.HmlrCommand


class UnknownHmlrCommand(command: HmlrCommand) : Throwable("Wrong command $command at mapping toTransport stage")
