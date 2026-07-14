package com.tomas.inventory_api.repository

import com.tomas.inventory_api.dto.category.Category
import com.tomas.inventory_api.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

// O repository é a camada que fala com a BD.
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Category?
    fun existsByName(name: String): Boolean
}