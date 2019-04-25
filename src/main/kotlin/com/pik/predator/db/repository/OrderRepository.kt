package com.pik.predator.db.repository

import com.pik.predator.db.data.Order
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderRepository : MongoRepository<Order, Int> {

}