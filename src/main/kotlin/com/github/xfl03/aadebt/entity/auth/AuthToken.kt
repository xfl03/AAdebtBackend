package com.github.xfl03.aadebt.entity.auth

import javax.persistence.*

/**
 * Token Entity
 */
@Entity
data class AuthToken(
        /**
         * Token id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int,
        /**
         * Token
         */
        @Column(nullable = false)
        var token: String,
        /**
         * User id
         */
        @Column(nullable = false)
        var userId: Int
)