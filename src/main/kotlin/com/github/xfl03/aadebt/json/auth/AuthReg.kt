package com.github.xfl03.aadebt.json.auth

import com.github.xfl03.aadebt.json.Response
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class AuthRegRequest(
        @field:NotEmpty
        var name: String,
        @field:Email(message = "Not a valid email")
        var email: String,
        @field:NotEmpty
        var password: String
)

data class AuthRegResponse(var id: Int, var token: String) : Response()