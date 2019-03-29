package com.github.xfl03.aadebt.json.debt

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class DebtAddRequest(
        var groupId: Int,
        @field:NotBlank(message = "Name requested")
        var name: String,
        @field:Min(value = 0, message = "Amount should more than 0")
        var amount: Int,
        var type: Int
)