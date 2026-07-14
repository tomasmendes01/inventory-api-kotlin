package com.tomas.inventory_api.exception.product

class ProductNotFoundException(id: Long) : RuntimeException("O produto com id $id não foi encontrado.")