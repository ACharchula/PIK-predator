package com.pik.predator.db.repository

import com.pik.predator.db.entities.Product
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository : MongoRepository<Product, Int>, ProductRepositoryCustom {

}