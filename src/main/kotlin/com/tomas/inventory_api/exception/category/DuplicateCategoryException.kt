package com.tomas.inventory_api.exception.category

class DuplicateCategoryException(name: String) : RuntimeException("A categoria $name já se encontra registada.")