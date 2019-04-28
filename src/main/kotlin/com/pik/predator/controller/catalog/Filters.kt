package com.pik.predator.controller.catalog

import com.pik.predator.db.data.Product
import java.math.BigDecimal
import kotlin.reflect.KProperty1

class Filters(private val map: Map<String, String>) {

    abstract inner class Filter<T>(
            protected val property: KProperty1<Product, T>,
            alternativeName: String?
    ) {
        protected val list = acceptedValuesOf(alternativeName ?: property.name)
        fun accept(product: Product): Boolean  = doAccept(property.get(product))
        abstract fun doAccept(value: T): Boolean
    }

    inner class ListedValuesFilter(
            property: KProperty1<Product, String>,
            alternativeName: String? = null
    ) : Filter<String>(property, alternativeName) {
        override fun doAccept(value: String): Boolean =
            list.contains(value) || list.isEmpty()
    }

    inner class ListToListFilter(
            property: KProperty1<Product, List<String>>,
            alternativeName: String? = null
    ) : Filter<List<String>>(property, alternativeName) {
        override fun doAccept(value: List<String>): Boolean =
            value.containsAll(list)
    }

    inner class AtLeastOneValueMatchFilter(
            property: KProperty1<Product, String>,
            alternativeName: String? = null
    ) : Filter<String>(property, alternativeName) {
        override fun doAccept(value: String): Boolean =
            list.any { manufacturer -> value.contains(manufacturer) } || list.isEmpty()
    }

    inner class RangeFilter<T: Comparable<T>>(
            private val from: T,
            private val to: T,
            property: KProperty1<Product, T>,
            alternativeName: String? = null
    ) : Filter<T>(property, alternativeName) {
        override fun doAccept(value: T): Boolean =
            value in from..to
    }

    fun accept(item: Product) = allFilters.all { it.accept(item) }

    private val allFilters = listOf(
            ListedValuesFilter(Product::processor),
            ListedValuesFilter(Product::processorClock),
            ListedValuesFilter(Product::type),
            ListedValuesFilter(Product::manufacturer),
            ListedValuesFilter(Product::operatingSystem),
            ListedValuesFilter(Product::hardDrive),
            ListedValuesFilter(Product::memorySize),
            ListedValuesFilter(Product::graphicVRAM),
            ListedValuesFilter(Product::ramType),
            ListedValuesFilter(Product::ramSize),
            ListedValuesFilter(Product::displayType),
            ListedValuesFilter(Product::displayResolution),
            ListedValuesFilter(Product::screenSize),
            ListedValuesFilter(Product::color),
            ListedValuesFilter(Product::warranty),
            AtLeastOneValueMatchFilter(Product::graphicCard, "graphicCardManufacturer"),
            ListToListFilter(Product::portTypes),
            RangeFilter(
                    map["priceFrom"]?.toBigDecimal() ?: BigDecimal.ZERO,
                    map["priceTo"]?.toBigDecimal() ?: BigDecimal(Int.MAX_VALUE),
                    Product::price
            ),
            RangeFilter(
                    map["weightFrom"]?.toFloat() ?: 0f,
                    map["weightTo"]?.toFloat() ?: Float.MAX_VALUE,
                    Product::weight
            )
    )

    private fun acceptedValuesOf(filterName: String): List<String> {
        return map
                .filterKeys { it.contains(filterName) }
                .values.toList()
    }

    operator fun get(filter: String) = map[filter]
}