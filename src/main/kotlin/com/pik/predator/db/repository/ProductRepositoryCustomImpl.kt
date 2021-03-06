package com.pik.predator.db.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate
) : ProductRepositoryCustom {

    override fun getDistinctValuesForAttribute(attributeName: String): List<String>? {
        val list = mutableListOf<String>()
        mongoTemplate.getCollection("Products").distinct(attributeName, String::class.java).into(list)
        return if (list.isNotEmpty()) list else null
    }
}