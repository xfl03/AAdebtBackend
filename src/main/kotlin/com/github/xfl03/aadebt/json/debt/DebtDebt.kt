package com.github.xfl03.aadebt.json.debt

import com.github.xfl03.aadebt.json.Response

data class DebtDebtRequest(val groupId: Int)
data class DebtDebtResponse(val debts: List<DebtDetailInfo>) : Response()