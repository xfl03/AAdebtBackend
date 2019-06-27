package com.github.xfl03.aadebt.json.debt

import java.sql.Date
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class DebtEditRequest(
        val groupId: Int,
        val debtId: Int,
        @field:NotBlank(message = "Name requested")
        var name: String,
        @field:Min(value = 0, message = "Amount should more than 0")
        var amount: Int,
        @field:Min(value = 0, message = "Type should more than 0")
        @field:Max(value = 12, message = "Type should less than 12")
        var type: Int,
        var date: Date
)