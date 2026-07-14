package com.tomas.inventory_api.exception.product

class DuplicateSkuException(sku: String) : RuntimeException("O produto com SKU $sku já se encontra registado.")