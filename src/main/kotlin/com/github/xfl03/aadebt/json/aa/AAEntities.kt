package com.github.xfl03.aadebt.json.aa

import javax.validation.constraints.Min

data class GroupInfo(var groupId: Int, var name: String, var locked: Boolean)

data class DebtDetailInfo(var debtId: Int, var name: String, var payer: PartInfo, var amount: Int)

data class PartInfo(var partId: Int, var name: String)

data class DetailInfo(
        var partId: Int,
        @field:Min(value = 0, message = "Amount should more than 0")
        var amount: Int
)

data class PartDetailInfo(var partId: Int, var name: String, var paid: Int, var total: Int)

data class PartPayInfo(var from: PartInfo, var to: PartInfo, var amount: Int)