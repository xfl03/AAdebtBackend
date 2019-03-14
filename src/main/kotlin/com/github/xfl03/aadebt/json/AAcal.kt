package com.github.xfl03.aadebt.json

data class AAcalRequest(var groupId: Int)

data class AAcalResponse(var groupId: Int, var detail: List<PartDetailInfo>, var pay: List<PartPayInfo>): Response()