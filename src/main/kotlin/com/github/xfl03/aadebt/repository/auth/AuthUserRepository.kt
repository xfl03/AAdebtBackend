package com.github.xfl03.aadebt.repository.auth

import com.github.xfl03.aadebt.entity.auth.AuthUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthUserRepository : JpaRepository<AuthUser, Int> {
    fun findByAccount(account: String): Optional<AuthUser>
    fun findByAccountAndPassword(account: String, password: String): Optional<AuthUser>
}