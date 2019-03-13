package com.github.xfl03.aadebt.entity

import javax.persistence.*

/**
 * 账目信息
 */
@Entity
data class DebtInfo(

        /**
         * 账目id
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        /**
         * 账目组id
         */
        @Column(nullable = false)
        var groupId: Int = 0,

        /**
         * 账目名称
         */
        @Column(nullable = false)
        var name: String = "",

        /**
         * 账目付款者id
         */
        @Column(nullable = false)
        var payerId: Int = 0,

        /**
         * 账目总额（单位：分）
         */
        @Column(nullable = false)
        var amount: Int = 0
)