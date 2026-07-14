package com.tomas.inventory_api.mapper

import com.tomas.inventory_api.dto.category.Category
import com.tomas.inventory_api.dto.product.ProductRequest
import com.tomas.inventory_api.dto.product.ProductResponse
import com.tomas.inventory_api.entity.Product
import com.tomas.inventory_api.repository.CategoryRepository
import com.tomas.inventory_api.service.CategoryService
import org.springframework.stereotype.Component

//Responsável por converter: Entity <-> DTO
@Component
class ProductMapper(private val categoryMapper: CategoryMapper) {

    fun toResponse(product: Product): ProductResponse {
        return ProductResponse(
            id = product.id,
            name = product.name,
            sku = product.sku,
            description = product.description,
            quantity = product.quantity,
            category = product.category?.let(categoryMapper::toResponse),
        )
    }

    fun toEntity(request: ProductRequest, category: Category): Product {
        return Product(
            name = request.name,
            sku = request.sku,
            description = request.description,
            quantity = request.quantity,
            category = category
        )
    }

    fun updateProduct(product: Product, request: ProductRequest, category: Category): Product {
        return product.copy(
            name = request.name,
            sku = request.sku,
            description = request.description,
            quantity = request.quantity,
            category = category
        )
    }
}