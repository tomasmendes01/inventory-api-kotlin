package com.tomas.inventory_api.mapper

import com.tomas.inventory_api.dto.category.Category
import com.tomas.inventory_api.dto.category.CategoryRequest
import com.tomas.inventory_api.dto.category.CategoryResponse
import org.springframework.stereotype.Component

@Component
class CategoryMapper {

    fun toResponse(category: Category): CategoryResponse {
        return CategoryResponse(
            id = category.id,
            name = category.name,
        )
    }

    fun toEntity(category: CategoryRequest): Category {
        return Category(
            name = category.name,
        )
    }
}