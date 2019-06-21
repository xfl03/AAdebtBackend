package com.github.xfl03.aadebt.json.aa

import com.github.xfl03.aadebt.json.Response

data class AAdebtRequest(var groupId: Int)

data class AAdebtResponse(var groupId: Int, var name: String, var debts: List<DebtDetailInfo>) : Response()