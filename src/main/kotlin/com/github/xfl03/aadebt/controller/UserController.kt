package com.github.xfl03.aadebt.controller

import com.github.xfl03.aadebt.entity.auth.AuthUserDetail
import com.github.xfl03.aadebt.json.CommonResponse
import com.github.xfl03.aadebt.json.Response
import com.github.xfl03.aadebt.json.user.UserInfo
import com.github.xfl03.aadebt.repository.auth.AuthTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController {
    @Autowired
    private lateinit var tokenRepo: AuthTokenRepository

    @RequestMapping(path = ["/api/user/info"], method = [RequestMethod.GET, RequestMethod.POST])
    fun info(): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)
        return UserInfo(obj.id, obj.name)
    }

    @RequestMapping(path = ["/api/user/logout"], method = [RequestMethod.GET, RequestMethod.POST])
    fun logout(): Response {
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)
        tokenRepo.deleteAllByToken(obj.token)
        return CommonResponse("Log out successful", 0)
    }
}