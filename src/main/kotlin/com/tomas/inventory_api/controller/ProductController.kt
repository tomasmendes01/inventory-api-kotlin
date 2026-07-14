package com.tomas.inventory_api.controller

import com.tomas.inventory_api.dto.product.ProductRequest
import com.tomas.inventory_api.dto.product.ProductResponse
import com.tomas.inventory_api.service.ProductService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// A API pública
@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
) {
    @GetMapping
    fun findAll(): List<ProductResponse> {
        return productService.getProducts()
    }

    @PostMapping
    fun create(@Valid @RequestBody request: ProductRequest): ProductResponse {
        return productService.createProduct(request)
    }

    @GetMapping("/{id}")
    fun findById(id: Long): ProductResponse {
        return productService.getProductById(id)
    }

    @PutMapping("/{id}")
    fun update(id: Long, @Valid @RequestBody request: ProductRequest): ProductResponse {
        return productService.updateProduct(id, request)
    }

    @DeleteMapping("/{id}")
    fun delete(id: Long) {
        return productService.deleteProduct(id)
    }
}