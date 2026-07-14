package com.tomas.inventory_api.dto.product

import com.tomas.inventory_api.dto.category.CategoryResponse

data class ProductResponse(
    val id: Long,
    val name: String,
    val sku: String,
    val description: String?,
    val quantity: Int,
    val category: CategoryResponse?,
)
