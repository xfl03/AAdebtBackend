package com.github.xfl03.aadebt.json

data class AAdebtRequest(var groupId: Int)

data class AAdebtResponse(var groupId: Int, var debts: List<DebtDetailInfo>): Response()