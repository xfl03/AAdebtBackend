package com.github.xfl03.aadebt.controller

import com.github.xfl03.aadebt.json.Response
import com.github.xfl03.aadebt.json.auth.AuthLoginRequest
import com.github.xfl03.aadebt.json.auth.AuthRegRequest
import com.github.xfl03.aadebt.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class AuthController {
    @Autowired
    private lateinit var service: AuthService

    @RequestMapping(path = ["/api/auth/reg"], method = [RequestMethod.POST])
    fun reg(req: AuthRegRequest): Response {
        return service.register(req)
    }

    @RequestMapping(path = ["/api/auth/login"], method = [RequestMethod.POST])
    fun login(req: AuthLoginRequest): Response {
        return service.login(req)
    }
}