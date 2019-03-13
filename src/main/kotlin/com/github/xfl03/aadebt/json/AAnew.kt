package com.github.xfl03.aadebt.json

data class AAnewRequest(var name: String, var parts: List<String>)

data class AAnewResponce(var groupId: Int, var name: String, var parts: List<PartInfo>)