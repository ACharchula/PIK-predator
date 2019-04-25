package com.pik.predator.databaseClasses.repositories

import com.pik.predator.databaseClasses.Notebook
import org.springframework.data.mongodb.repository.MongoRepository

interface NotebooksRepository : MongoRepository<Notebook, Int> {
}