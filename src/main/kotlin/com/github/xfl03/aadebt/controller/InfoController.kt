package com.github.xfl03.aadebt.controller

import com.github.xfl03.aadebt.json.CommonResponse
import com.github.xfl03.aadebt.json.Response
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class InfoController {
    @RequestMapping(path = ["/api/info"], method = [RequestMethod.GET, RequestMethod.POST])
    fun info(): Response {
        return CommonResponse("Proudly powered by Spring Boot.", 0)
    }
}