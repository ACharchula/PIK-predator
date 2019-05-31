package com.pik.predator.db.repository

import com.pik.predator.db.entities.Cart
import org.springframework.data.mongodb.repository.MongoRepository

interface CartRepository: MongoRepository<Cart, Int> {

    fun findByUserId(userId: Int): Cart?
}