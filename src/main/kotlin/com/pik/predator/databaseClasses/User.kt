package com.pik.predator.databaseClasses

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Users")
class User(login: String, password: String) {

    @Id
    var login: String? = login

    var password: String? = password

    override fun toString() : String {
        return String.format("User[login=%s, password='%s']", login, password)
    }
}