package com.pik.predator.db.repository

import com.pik.predator.db.entities.Order
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderRepository : MongoRepository<Order, Int> {

    fun findByUserId(userId: String): List<Order>
}