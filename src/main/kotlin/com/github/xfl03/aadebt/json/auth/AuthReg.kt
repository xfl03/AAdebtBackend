package com.github.xfl03.aadebt.json.auth

import com.github.xfl03.aadebt.json.Response
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class AuthRegRequest(
        @field:NotEmpty(message = "Name is requested")
        var name: String,
        @field:Email(message = "Not a valid email")
        var email: String,
        @field:NotEmpty(message = "Password is requested")
        var password: String
)

data class AuthRegResponse(var id: Int, var token: String) : Response()