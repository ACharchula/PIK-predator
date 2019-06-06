package com.pik.predator.helpers

inline fun <T: Any> T?.alsoNullable(onNotNull: (T) -> Unit, onNull: () -> Unit): T? {
    if (this != null) onNotNull(this)
    else onNull()
    return this
}

inline fun <T: Any> T?.applyNullable(onNotNull: T.() -> Unit, onNull: () -> Unit): T? {
    if (this != null) this.onNotNull()
    else onNull()
    return this
}

inline fun <T: Any, R: Any> T?.letNullable(onNotNull: (T) -> R, onNull: () -> R): R {
    return if (this != null) onNotNull(this)
    else onNull()
}

class NonNullMap<K, V>(
    private val map: Map<K, V>,
    private val defaultValue: V
) : Map<K, V> by map {

    override fun get(key: K): V? {
        return map.getOrDefault(key, defaultValue)
    }
}

class NonNullMutableMap<K, V>(
    private val map: MutableMap<K, V>,
    private val defaultValue: V
): MutableMap<K, V> by map {

    override operator fun get(key: K): V {
        return map.getOrDefault(key, defaultValue)
    }
}

fun <K, V> Map<K, V>.nonNull(defaultValue: V): NonNullMap<K, V> {
    return NonNullMap(this, defaultValue)
}

fun <K, V> nonNullMapOf(defaultValue: V, vararg pairs: Pair<K, V> = emptyArray()): NonNullMap<K, V> {
    return mapOf(*pairs).nonNull(defaultValue)
}

fun <K, V> MutableMap<K, V>.nonNull(defaultValue: V): NonNullMutableMap<K, V> {
    return NonNullMutableMap(this, defaultValue)
}

fun <K, V> nonNullMutableMapOf(defaultValue: V, vararg pairs: Pair<K, V> = emptyArray()): NonNullMutableMap<K, V> {
    return mutableMapOf(*pairs).nonNull(defaultValue)
}