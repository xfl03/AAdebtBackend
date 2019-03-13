package com.github.xfl03.aadebt.entity

import javax.persistence.*

/**
 * 账目详情
 */
@Entity
data class DebtDetail(

        /**
         * 详情id
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
         * 账目id
         */
        @Column(nullable = false)
        var debtId: Int = 0,

        /**
         * 参与者id
         */
        @Column(nullable = false)
        var partId: Int = 0,

        /**
         * 金额（单位：分）
         */
        @Column(nullable = false)
        var amount: Int = 0
)