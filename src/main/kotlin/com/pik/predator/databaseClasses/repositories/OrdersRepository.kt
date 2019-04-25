package com.pik.predator.databaseClasses.repositories

import com.pik.predator.databaseClasses.Order
import org.springframework.data.mongodb.repository.MongoRepository

interface OrdersRepository : MongoRepository<Order, Int> {
}