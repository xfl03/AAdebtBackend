package com.github.xfl03.aadebt.repository.auth

import com.github.xfl03.aadebt.entity.auth.AuthToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthTokenRepository : JpaRepository<AuthToken, Int> {
    fun findByToken(token: String): Optional<AuthToken>
    fun deleteAllByToken(token: String)
}