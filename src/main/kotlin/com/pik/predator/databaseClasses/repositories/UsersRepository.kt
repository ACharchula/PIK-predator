package com.pik.predator.databaseClasses.repositories

import com.pik.predator.databaseClasses.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UsersRepository : MongoRepository<User, String> {

    fun findByLogin(login: String): User
    fun findByPassword(password: String): List<User>

}