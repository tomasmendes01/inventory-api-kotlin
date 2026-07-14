package com.tomas.inventory_api.dto.category

import jakarta.validation.constraints.NotBlank

data class CategoryRequest(
    @field:NotBlank
    val name: String,
)
