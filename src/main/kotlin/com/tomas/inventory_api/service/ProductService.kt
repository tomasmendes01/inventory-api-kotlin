package com.tomas.inventory_api.service

import com.tomas.inventory_api.dto.product.ProductRequest
import com.tomas.inventory_api.dto.product.ProductResponse
import com.tomas.inventory_api.exception.category.CategoryNotFoundException
import com.tomas.inventory_api.exception.product.DuplicateSkuException
import com.tomas.inventory_api.exception.product.ProductNotFoundException
import com.tomas.inventory_api.mapper.ProductMapper
import com.tomas.inventory_api.repository.CategoryRepository
import com.tomas.inventory_api.repository.ProductRepository
import org.springframework.stereotype.Service

// Aqui fica a lógica de negócio
@Service
class ProductService(
    private val repository: ProductRepository,
    private val mapper: ProductMapper,
    private val categoryRepository: CategoryRepository
) {
    fun getProducts(): List<ProductResponse> {
        return repository.findAll().map(mapper::toResponse)
    }

    fun createProduct(request: ProductRequest): ProductResponse {
        if (repository.existsBySku(request.sku)) {
            throw DuplicateSkuException(request.sku)
        }

        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { CategoryNotFoundException(request.categoryId) }

        val product = mapper.toEntity(request, category)
        val saved = repository.save(product)

        return mapper.toResponse(saved)
    }

    fun getProductById(id: Long): ProductResponse {
        val product = repository.findById(id).orElseThrow { ProductNotFoundException(id) }
        return mapper.toResponse(product)
    }

    fun updateProduct(id: Long, request: ProductRequest): ProductResponse {
        val product = repository.findById(id).orElseThrow { ProductNotFoundException(id) }
        val category = categoryRepository.findById(request.categoryId).orElseThrow { CategoryNotFoundException(id) }

        val updated = mapper.updateProduct(product, request, category)
        val saved = repository.save(updated)
        return mapper.toResponse(saved)
    }

    fun deleteProduct(id: Long) {
        val product = repository.findById(id).orElseThrow { ProductNotFoundException(id) }
        repository.delete(product)
    }

}