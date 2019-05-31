package com.pik.predator.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.db.entities.DatabaseSequence
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.test.context.junit4.SpringRunner
import org.junit.Assert.*

@RunWith(SpringRunner::class)
class IncrementalSequenceGeneratorTest {

    companion object {
        private const val SEQUENCE_NAME = "SOME_SEQUENCE_NAME"
    }

    @Mock private lateinit var mongoOperations: MongoOperations
    private lateinit var sequenceGenerator: SequenceGenerator

    @Before
    fun setup() {
        whenever(mongoOperations.findAndModify(any(), any(), any(), eq(DatabaseSequence::class.java)))
            .thenReturn(DatabaseSequence("1", 8))

        sequenceGenerator = IncrementalSequenceGenerator(mongoOperations)
    }

    @Test
    fun `when get next id then findAndModify is called on mongoOperations with proper arguments`() {
        assertEquals(
            8,
            sequenceGenerator.nextId(SEQUENCE_NAME)
        )
    }
}