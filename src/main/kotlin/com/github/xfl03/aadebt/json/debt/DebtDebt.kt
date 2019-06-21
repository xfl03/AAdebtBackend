package com.github.xfl03.aadebt.json.debt

import com.github.xfl03.aadebt.json.Response

data class DebtDebtRequest(val groupId: Int)
data class DebtDebtResponse(var name: String, val debts: List<DebtDetailInfo>) : Response()