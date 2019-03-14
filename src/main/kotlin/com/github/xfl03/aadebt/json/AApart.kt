package com.github.xfl03.aadebt.json

data class AApartRequest(var groupId: Int)

data class AApartResponse(var groupId: Int, var parts: List<PartInfo>): Response()
