package com.github.xfl03.aadebt.json

data class AApartRequest(var groupId: Int)

data class AApartResponce(var groupId: Int, var parts: List<PartInfo>)
