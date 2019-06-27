package com.github.xfl03.aadebt.controller

import com.github.xfl03.aadebt.json.Response
import com.github.xfl03.aadebt.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @Autowired
    private lateinit var service: AuthService

    @RequestMapping(path = ["/api/user/info"], method = [RequestMethod.GET, RequestMethod.POST])
    fun info(): Response {
        return service.info()
    }

    @RequestMapping(path = ["/api/user/logout"], method = [RequestMethod.GET, RequestMethod.POST])
    fun logout(): Response {
        return service.logout()
    }
}