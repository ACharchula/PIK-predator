package com.pik.predator.db.repository

import com.pik.predator.db.data.Cart
import org.springframework.data.mongodb.repository.MongoRepository

interface CartRepository: MongoRepository<Cart, Int> {

    fun findByUserId(userId: Int): Cart?
}