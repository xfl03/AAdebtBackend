package com.github.xfl03.aadebt.json

data class CommonResponse(var msg: String, var code: Int): Response()

open class Response