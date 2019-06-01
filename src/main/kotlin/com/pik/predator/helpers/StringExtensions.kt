package com.pik.predator.helpers

fun String.containsIgnoreCase(substring: String) =
    this.contains(substring, ignoreCase = true)