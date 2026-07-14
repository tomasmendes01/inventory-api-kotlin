package com.tomas.inventory_api.exception.category

class CategoryNotFoundException(id: Long) : RuntimeException("A categoria com id $id não foi encontrada.")