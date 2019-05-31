package com.pik.predator.service

import com.pik.predator.db.entities.DatabaseSequence
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

interface SequenceGenerator {
    fun nextId(sequenceName: String): Int
}

@Service
class IncrementalSequenceGenerator(
    private val mongoOperations: MongoOperations
) : SequenceGenerator {

    override fun nextId(sequenceName: String): Int {
        return mongoOperations.findAndModify(
            Query.query(Criteria.where("_id").`is`(sequenceName)),
            Update().inc("seq", 1),
            FindAndModifyOptions.options().returnNew(true).upsert(true),
            DatabaseSequence::class.java
        ).let { counter: DatabaseSequence? ->
            counter?.seq ?: 1
        }
    }
}