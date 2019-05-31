package com.pik.predator.db.repository

interface ProductRepositoryCustom {

    fun getDistinctValuesForAttribute(attributeName: String): List<String>?
}