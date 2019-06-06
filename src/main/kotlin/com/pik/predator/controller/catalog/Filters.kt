package com.pik.predator.controller.catalog

import com.pik.predator.db.entities.Product
import com.pik.predator.helpers.containsAllIgnoreCase
import com.pik.predator.helpers.containsIgnoreCase
import java.math.BigDecimal
import kotlin.reflect.KProperty1

interface Filter<T> {
    val filtersHub: FiltersHub
    val property: KProperty1<Product, T>
    val alternativeName: String?

    val acceptedValues: List<String>
        get() = filtersHub.acceptedValuesOf(alternativeName ?: property.name)

    fun accept(product: Product): Boolean =
        doAccept(property.get(product))

    fun doAccept(value: T): Boolean
}

class ListedValuesFilter(
    override val filtersHub: FiltersHub,
    override val property: KProperty1<Product, String>,
    override val alternativeName: String? = null
) : Filter<String> {

    override fun doAccept(value: String): Boolean =
        acceptedValues.containsIgnoreCase(value) || acceptedValues.isEmpty()
}

class ListToListFilter(
    override val filtersHub: FiltersHub,
    override val property: KProperty1<Product, List<String>>,
    override val alternativeName: String? = null
) : Filter<List<String>> {

    override fun doAccept(value: List<String>): Boolean =
        value.containsAllIgnoreCase(acceptedValues)
}

class IsContainedByAtLeastOneAcceptedValueFilter(
    override val filtersHub: FiltersHub,
    override val property: KProperty1<Product, String>,
    override val alternativeName: String? = null
) : Filter<String> {

    override fun doAccept(value: String): Boolean =
        acceptedValues.any { acceptedValue -> value.containsIgnoreCase(acceptedValue) } || acceptedValues.isEmpty()
}

interface RangeFilter<T : Comparable<T>> : Filter<T> {
    val valueParser: (String?) -> T?
    val minValue: T
    val maxValue: T

    private val from: T
        get() = valueParser(filtersHub.map[property.name + "From"]) ?: minValue

    private val to: T
        get() = valueParser(filtersHub.map[property.name + "To"]) ?: maxValue

    override fun doAccept(value: T): Boolean =
        value in from..to
}

class IntRangeFilter (
    override val filtersHub: FiltersHub,
    override val property: KProperty1<Product, Int>,
    override val alternativeName: String? = null
) : RangeFilter<Int> {
    override val valueParser: (String?) -> Int? = { text -> text?.toInt() }
    override val minValue: Int get() = Int.MIN_VALUE
    override val maxValue: Int get() = Int.MAX_VALUE
}

class FloatRangeFilter (
    override val filtersHub: FiltersHub,
    override val property: KProperty1<Product, Float>,
    override val alternativeName: String? = null
) : RangeFilter<Float> {
    override val valueParser: (String?) -> Float? = { text -> text?.toFloat() }
    override val minValue: Float get() = Float.MIN_VALUE
    override val maxValue: Float get() = Float.MAX_VALUE
}

class BigDecimalRangeFilter (
    override val filtersHub: FiltersHub,
    override val property: KProperty1<Product, BigDecimal>,
    override val alternativeName: String? = null
) : RangeFilter<BigDecimal> {
    override val valueParser: (String?) -> BigDecimal? = { text -> text?.toBigDecimal() }
    override val minValue: BigDecimal get() = BigDecimal(Int.MIN_VALUE)
    override val maxValue: BigDecimal get() = BigDecimal(Int.MAX_VALUE)
}