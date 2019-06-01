package com.pik.predator.helpers

fun <T> emptyMutableList(): MutableList<T> = emptyList<T>().toMutableList()

fun Collection<String>.containsIgnoreCase(elem: String) =
    this.any { it.toLowerCase() == elem.toLowerCase() }

fun Collection<String>.containsAllIgnoreCase(list: List<String>) =
    list.all { this.containsIgnoreCase(it) }