package com.pik.predator.controller.catalog

import com.pik.predator.db.entities.Product
import com.pik.predator.helpers.containsAllIgnoreCase
import com.pik.predator.helpers.containsIgnoreCase
import java.math.BigDecimal
import kotlin.reflect.KProperty1

class Filters(private val map: Map<String, String>) {

    abstract inner class Filter<T>(
            protected val property: KProperty1<Product, T>,
            alternativeName: String?
    ) {
        protected val acceptedValues: List<String> = acceptedValuesOf(alternativeName ?: property.name)
        fun accept(product: Product): Boolean = doAccept(property.get(product))
        abstract fun doAccept(value: T): Boolean
    }

    inner class ListedValuesFilter(
            property: KProperty1<Product, String>,
            alternativeName: String? = null
    ) : Filter<String>(property, alternativeName) {
        override fun doAccept(value: String): Boolean = acceptedValues.containsIgnoreCase(value) || acceptedValues.isEmpty()
    }

    inner class ListToListFilter(
            property: KProperty1<Product, List<String>>,
            alternativeName: String? = null
    ) : Filter<List<String>>(property, alternativeName) {
        override fun doAccept(value: List<String>): Boolean = value.containsAllIgnoreCase(acceptedValues)
    }

    inner class IsContainedByAtLeastOneAcceptedValueFilter(
            property: KProperty1<Product, String>,
            alternativeName: String? = null
    ) : Filter<String>(property, alternativeName) {
        override fun doAccept(value: String): Boolean = acceptedValues.any { acceptedValue -> value.containsIgnoreCase(acceptedValue) } || acceptedValues.isEmpty()
    }

    inner class RangeFilter<T : Comparable<T>>(
            valueParser: (String?) -> T?,
            minValue: T,
            maxValue: T,
            property: KProperty1<Product, T>,
            alternativeName: String? = null
    ) : Filter<T>(property, alternativeName) {
        private val from = valueParser(map["${property.name}From"]) ?: minValue
        private val to = valueParser(map["${property.name}To"]) ?: maxValue
        override fun doAccept(value: T): Boolean = value in from..to
    }

    fun accept(item: Product) = allFilters.all { it.accept(item) }

    private val allFilters = listOf(
        ListedValuesFilter(Product::processor),
        ListedValuesFilter(Product::type),
        ListedValuesFilter(Product::manufacturer),
        ListedValuesFilter(Product::operatingSystem),
        ListedValuesFilter(Product::hardDriveType),
        ListedValuesFilter(Product::ramType),
        ListedValuesFilter(Product::displayType),
        ListedValuesFilter(Product::displayResolution),
        ListedValuesFilter(Product::screenSize),
        ListedValuesFilter(Product::color),
        ListedValuesFilter(Product::warranty),
        IsContainedByAtLeastOneAcceptedValueFilter(Product::graphicCard, "graphicCardManufacturer"),
        ListToListFilter(Product::portTypes, "portType"),
        RangeFilter<BigDecimal>({ it?.toBigDecimal() }, BigDecimal.ZERO, BigDecimal(Int.MAX_VALUE), Product::price),
        RangeFilter( { it?.toFloat() }, 0f, Float.MAX_VALUE, Product::weight),
        RangeFilter( { it?.toInt() }, 0, Int.MAX_VALUE, Product::hardDriveSize),
        RangeFilter( { it?.toInt() }, 0, Int.MAX_VALUE, Product::graphicVRAM),
        RangeFilter( { it?.toInt() }, 0, Int.MAX_VALUE, Product::ramSize)
    )

    private fun acceptedValuesOf(filterName: String): List<String> {
        return map
            .filterKeys { it.contains(filterName) }
            .values.toList()
    }

    operator fun get(filter: String) = map[filter]
}