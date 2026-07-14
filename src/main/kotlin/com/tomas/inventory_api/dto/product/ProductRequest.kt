package com.tomas.inventory_api.dto.product

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero

data class ProductRequest(

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val sku: String,

    val description: String?,

    @field:PositiveOrZero
    val quantity: Int,

    val categoryId: Long,
)
