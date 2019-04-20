package com.pik.predator

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.pik.predator.databaseClasses.User
import com.pik.predator.databaseClasses.repositories.UsersRepository
import org.springframework.beans.factory.annotation.Autowired

@RestController
class HelloController {

    @Autowired
    private var repository: UsersRepository? = null

    @RequestMapping("/api/hello")
    fun hello(): String {
        return "tak to ja, PREDATOR"
    }

    @RequestMapping("/api/example")
    fun example(): String {

        repository?.deleteAll()
        repository?.save(User("Wisza", "haslo"))
        repository?.save(User("Antonio", "haslo"))

        for (customer in repository?.findAll()!!) {
            println(customer)
        }

        println()
        println(repository!!.findByLogin("Wisza"))
        println(repository!!.findByPassword("haslo"))

        return "cokolwiek"
    }
}