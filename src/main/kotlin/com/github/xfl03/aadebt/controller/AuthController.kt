package com.github.xfl03.aadebt.controller

import com.github.xfl03.aadebt.json.CommonResponse
import com.github.xfl03.aadebt.json.Response
import com.github.xfl03.aadebt.json.auth.AuthLoginRequest
import com.github.xfl03.aadebt.json.auth.AuthRegRequest
import com.github.xfl03.aadebt.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
class AuthController {
    @Autowired
    private lateinit var service: AuthService

    @RequestMapping(path = ["/api/auth/reg"], method = [RequestMethod.POST])
    fun reg(@RequestBody @Validated req: AuthRegRequest, br: BindingResult): Response {
        if (br.hasErrors()) return CommonResponse(br.allErrors.stream().map { it.defaultMessage }.collect(Collectors.joining(" & ")), -400)

        return service.register(req)
    }

    @RequestMapping(path = ["/api/auth/login"], method = [RequestMethod.POST])
    fun login(@RequestBody @Validated req: AuthLoginRequest, br: BindingResult): Response {
        if (br.hasErrors()) return CommonResponse(br.allErrors.stream().map { it.defaultMessage }.collect(Collectors.joining(" & ")), -400)

        return service.login(req)
    }
}