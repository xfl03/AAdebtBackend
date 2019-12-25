package com.github.xfl03.aadebt.json.debt

import com.github.xfl03.aadebt.json.Response

data class DebtCalRequest(var groupId: Int)

data class DebtCalResponse(var daily: Int, var special: Int, var total: Int, var average: Int, var types: List<DebtTypeInfo>, var dates: List<DebtDateInfo>) : Response()