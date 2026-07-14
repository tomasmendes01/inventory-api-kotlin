package com.tomas.inventory_api.service

import com.tomas.inventory_api.dto.category.CategoryRequest
import com.tomas.inventory_api.dto.category.CategoryResponse
import com.tomas.inventory_api.dto.product.ProductRequest
import com.tomas.inventory_api.dto.product.ProductResponse
import com.tomas.inventory_api.exception.category.CategoryNotFoundException
import com.tomas.inventory_api.exception.category.DuplicateCategoryException
import com.tomas.inventory_api.exception.product.DuplicateSkuException
import com.tomas.inventory_api.exception.product.ProductNotFoundException
import com.tomas.inventory_api.mapper.CategoryMapper
import com.tomas.inventory_api.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val repository: CategoryRepository,
    private val mapper: CategoryMapper
) {
    fun getCategories(): List<CategoryResponse> {
        return repository.findAll().map(mapper::toResponse)
    }

    fun createCategory(request: CategoryRequest): CategoryResponse {
        if (repository.existsByName(request.name)) {
            throw DuplicateCategoryException(request.name)
        }
        val category = mapper.toEntity(request)
        val saved = repository.save(category)

        return mapper.toResponse(saved)
    }

    fun getCategoryById(id: Long): CategoryResponse {
        val category = repository.findById(id).orElseThrow { CategoryNotFoundException(id) }
        return mapper.toResponse(category)
    }
}