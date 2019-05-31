package com.pik.predator.db.repository

interface ProductRepositoryCustom {

    fun getAttributesInfo(attributeName: String): List<String>
}