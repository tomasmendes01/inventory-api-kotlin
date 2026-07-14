package com.tomas.inventory_api.repository

import com.tomas.inventory_api.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

// O repository é a camada que fala com a BD.
interface ProductRepository : JpaRepository<Product, Long> {
    fun findBySku(sku: String): Product?
    fun existsBySku(sku: String): Boolean
}