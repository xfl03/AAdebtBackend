package com.github.xfl03.aadebt.json.debt

import java.sql.*

data class DebtDetailInfo(var debtId: Int, var name: String, var amount: Int, var type: Int, var date: Date)

data class DebtTypeInfo(var type: Int, var amount: Int)

data class DebtDateInfo(var date: Date, var amount: Int)