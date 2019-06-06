package com.pik.predator.controller.catalog

import com.pik.predator.db.entities.Product
import com.pik.predator.helpers.containsAllIgnoreCase
import com.pik.predator.helpers.containsIgnoreCase
import java.math.BigDecimal
import kotlin.reflect.KProperty1

class FiltersHub(internal val map: Map<String, String>) {

    fun accept(item: Product) = allFilters.all { it.accept(item) }

    private val allFilters = listOf(
        ListedValuesFilter(this, Product::processor),
        ListedValuesFilter(this, Product::type),
        ListedValuesFilter(this, Product::manufacturer),
        ListedValuesFilter(this, Product::operatingSystem),
        ListedValuesFilter(this, Product::hardDriveType),
        ListedValuesFilter(this, Product::ramType),
        ListedValuesFilter(this, Product::displayType),
        ListedValuesFilter(this, Product::displayResolution),
        ListedValuesFilter(this, Product::screenSize),
        ListedValuesFilter(this, Product::color),
        ListedValuesFilter(this, Product::warranty),

        IsContainedByAtLeastOneAcceptedValueFilter(this, Product::graphicCard, "graphicCardManufacturer"),
        ListToListFilter(this, Product::portTypes, "portType"),

        BigDecimalRangeFilter(this, Product::price),
        FloatRangeFilter(this, Product::weight),
        IntRangeFilter(this, Product::hardDriveSize),
        IntRangeFilter(this, Product::graphicVRAM),
        IntRangeFilter(this, Product::ramSize)
    )

    internal fun acceptedValuesOf(filterName: String): List<String> {
        return map
            .filterKeys { it.contains(filterName) }
            .values
            .toList()
    }

    operator fun get(filter: String) = map[filter]
}