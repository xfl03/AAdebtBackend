package com.github.xfl03.aadebt.json.auth

import com.github.xfl03.aadebt.json.Response
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class AuthLoginRequest(
        @field:Email(message = "Not a valid email")
        var email: String,
        @field:NotBlank(message = "Password requested")
        var password: String
)

data class AuthLoginResponse(var id: Int, var token: String) : Response()
