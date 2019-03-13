package com.github.xfl03.aadebt.json

data class AADebtRequest(var groupId: Int)

data class AADebtResponce(var groupId: Int, var debts: List<DebtDetailInfo>)