package com.github.xfl03.aadebt.entity.debt

import java.sql.Date
import javax.persistence.*

/**
 * 普通账目信息
 */
@Entity
data class DebtNormalInfo(

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
         * 账目总额（单位：分）
         */
        @Column(nullable = false)
        var amount: Int = 0,

        /**
         * 账目类型
         */
        @Column(nullable = false)
        var type: Int = 0,

        /**
         * 账目日期
         */
        @Column(nullable = false)
        var date: Date = Date(System.currentTimeMillis())
)