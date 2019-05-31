package com.pik.predator.db.repository

import com.pik.predator.db.data.Product
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ProductRepository : MongoRepository<Product, Int>, ProductRepositoryCustom {

}