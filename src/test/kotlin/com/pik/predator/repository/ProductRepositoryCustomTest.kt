package com.pik.predator.repository

import com.mongodb.client.DistinctIterable
import com.mongodb.client.MongoCollection
import com.nhaarman.mockitokotlin2.whenever
import com.pik.predator.db.repository.ProductRepositoryCustom
import com.pik.predator.db.repository.ProductRepositoryCustomImpl
import org.bson.Document
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Spy
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class ProductRepositoryCustomTest {

    private lateinit var productRepository: ProductRepositoryCustom
    @Mock private lateinit var mongoTemplate: MongoTemplate
    @Mock private lateinit var mongoCollection: MongoCollection<Document>
    @Spy private lateinit var distinctIterable: DistinctIterableSpy

    abstract class DistinctIterableSpy : DistinctIterable<String> {
        override fun <A : MutableCollection<in String>?> into(target: A): A {
            target?.addAll(listOf("matte", "super-matte"))
            return target
        }
    }

    @Before
    fun setup() {
        whenever(mongoTemplate.getCollection("Products")).thenReturn(mongoCollection)
        whenever(mongoCollection.distinct("displayType", String::class.java)).thenReturn(distinctIterable)
        productRepository = ProductRepositoryCustomImpl(mongoTemplate)
    }

    @Test
    fun `test get distinct values for attribute`() {
        assertEquals(
            listOf("matte", "super-matte"),
            productRepository.getDistinctValuesForAttribute("displayType")
        )
    }
}