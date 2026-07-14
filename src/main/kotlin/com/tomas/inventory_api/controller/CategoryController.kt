package com.tomas.inventory_api.controller

import com.tomas.inventory_api.dto.category.CategoryRequest
import com.tomas.inventory_api.dto.category.CategoryResponse
import com.tomas.inventory_api.service.CategoryService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// A API pública
@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryService: CategoryService,
) {
    @GetMapping
    fun findAll(): List<CategoryResponse> {
        return categoryService.getCategories()
    }

    @PostMapping
    fun create(@Valid @RequestBody request: CategoryRequest): CategoryResponse{
        return categoryService.createCategory(request)
    }
}