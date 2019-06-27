package com.github.xfl03.aadebt.service

import com.github.xfl03.aadebt.entity.auth.AuthToken
import com.github.xfl03.aadebt.entity.auth.AuthUser
import com.github.xfl03.aadebt.entity.auth.AuthUserDetail
import com.github.xfl03.aadebt.json.CommonResponse
import com.github.xfl03.aadebt.json.Response
import com.github.xfl03.aadebt.json.auth.AuthLoginRequest
import com.github.xfl03.aadebt.json.auth.AuthLoginResponse
import com.github.xfl03.aadebt.json.auth.AuthRegRequest
import com.github.xfl03.aadebt.json.auth.AuthRegResponse
import com.github.xfl03.aadebt.json.user.UserInfo
import com.github.xfl03.aadebt.repository.auth.AuthTokenRepository
import com.github.xfl03.aadebt.repository.auth.AuthUserRepository
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken


@Service
class AuthService {
    @Autowired
    private lateinit var userRepo: AuthUserRepository
    @Autowired
    private lateinit var tokenRepo: AuthTokenRepository
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var service: AuthUserDetailService

    fun register(req: AuthRegRequest): Response {
        if (userRepo.findByAccount(req.email).isPresent) {
            return CommonResponse("Email exists.", -1);
        }
        val be = BCryptPasswordEncoder()
        var user = AuthUser(-1, req.name, req.email, be.encode(req.password))
        user = userRepo.save(user)

        var token = getRandomToken()
        while (tokenRepo.findByToken(token).isPresent) {
            token = getRandomToken()
        }
        var authToken = AuthToken(-1, token, user.id)
        tokenRepo.save(authToken)

        return AuthRegResponse(user.id, token)
    }

    fun login(req: AuthLoginRequest): Response {
        val upToken = UsernamePasswordAuthenticationToken(req.email, req.password)
        val authentication = authenticationManager.authenticate(upToken)
        SecurityContextHolder.getContext().authentication = authentication

        val user = service.loadUserByUsername(req.email) as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)

        var token = getRandomToken()
        while (tokenRepo.findByToken(token).isPresent) {
            token = getRandomToken()
        }
        var authToken = AuthToken(-1, token, user.id)
        tokenRepo.save(authToken)

        return AuthLoginResponse(user.id, token)
    }

    fun getRandomToken(): String {
        return Base64.encodeBase64String(UUID.randomUUID().toString().toByteArray())
    }
    fun info():Response{
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)
        return UserInfo(obj.id, obj.name)
    }
    fun logout():Response{
        val obj = SecurityContextHolder.getContext().authentication.principal as? AuthUserDetail
                ?: return CommonResponse("Internal Error", -500)
        tokenRepo.deleteByToken(obj.token)
        return CommonResponse("Log out successful", 0)
    }
}