package com.pik.predator.controller.catalog

import com.pik.predator.db.data.Product
import java.math.BigDecimal
import kotlin.reflect.KProperty1

class Filters(private val map: Map<String, String>) {

    abstract inner class Filter<T>(
            val property: KProperty1<Product, T>
    ) {
        protected val list = acceptedValuesOf(property.name)
        fun accept(product: Product): Boolean  = doAccept(property.get(product))
        abstract fun doAccept(value: T): Boolean
    }

    inner class ListedValuesFilter(
            property: KProperty1<Product, String>
    ) : Filter<String>(property) {
        override fun doAccept(value: String): Boolean =
            list.contains(value) || list.isEmpty()
    }

    inner class ListToListFilter(
            property: KProperty1<Product, List<String>>
    ) : Filter<List<String>>(property) {
        override fun doAccept(value: List<String>): Boolean =
            list.containsAll(value)
    }

    inner class AtLeastOneValueMatchFilter(
            property: KProperty1<Product, String>
    ) : Filter<String>(property) {
        override fun doAccept(value: String): Boolean =
            list.any { manufacturer -> value.contains(manufacturer) } || list.isEmpty()
    }

    inner class RangeFilter<T: Comparable<T>>(
            private val from: T,
            private val to: T,
            property: KProperty1<Product, T>
    ) : Filter<T>(property) {
        override fun doAccept(value: T): Boolean =
            value in from..to
    }

    val processorsFilter = ListedValuesFilter(Product::processor)
    val processorClocksFilter = ListedValuesFilter(Product::processorClock)
    val typesFilter = ListedValuesFilter(Product::type)
    val manufacturersFilter = ListedValuesFilter(Product::manufacturer)
    val operatingSystemsFilter = ListedValuesFilter(Product::operatingSystem)
    val portTypesFilter = ListToListFilter(Product::portTypes)
    val hardDriveFilter = ListedValuesFilter(Product::hardDrive)
    val memorySizesFilter = ListedValuesFilter(Product::memorySize)
    val graphicCardsManufacturersFilter = AtLeastOneValueMatchFilter(Product::graphicCard)
    val graphicVRAMsFilter = ListedValuesFilter(Product::graphicVRAM)
    val ramTypesFilter = ListedValuesFilter(Product::ramType)
    val ramSizesFilter = ListedValuesFilter(Product::ramSize)
    val displayTypesFilter = ListedValuesFilter(Product::displayType)
    val displayResolutionsFilter = ListedValuesFilter(Product::displayResolution)
    val screenSizesFilter = ListedValuesFilter(Product::screenSize)
    val colorsFilter = ListedValuesFilter(Product::color)
    val warrantiesFilter = ListedValuesFilter(Product::warranty)
    val priceFilter = RangeFilter(
            map["priceFrom"]?.toBigDecimal() ?: BigDecimal.ZERO,
            map["priceTo"]?.toBigDecimal() ?: BigDecimal(Int.MAX_VALUE),
            Product::price
    )
    val weightFilter = RangeFilter(
            map["weightFrom"]?.toFloat() ?: 0f,
            map["weightTo"]?.toFloat() ?: Float.MAX_VALUE,
            Product::weight
    )

    private fun acceptedValuesOf(filterName: String): List<String> {
        return map
                .filterKeys { it.contains(filterName) }
                .values.toList()
    }

    operator fun get(filter: String) = map[filter]
}