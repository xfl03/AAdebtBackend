package com.github.xfl03.aadebt.json.aa

import com.github.xfl03.aadebt.json.Response

data class AAcalRequest(var groupId: Int)

data class AAcalResponse(var groupId: Int, var detail: List<PartDetailInfo>, var pay: List<PartPayInfo>): Response()