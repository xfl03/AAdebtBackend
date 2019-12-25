package com.github.xfl03.aadebt.json.debt

import java.sql.*

data class DebtDetailInfo(var debtId: Int, var name: String, var amount: Int, var type: Int, var date: Date, var special: Boolean)

data class DebtTypeInfo(var type: Int, var daily: Int, var special: Int, var amount: Int)

data class DebtDateInfo(var date: Date, var daily: Int, var special: Int, var amount: Int)