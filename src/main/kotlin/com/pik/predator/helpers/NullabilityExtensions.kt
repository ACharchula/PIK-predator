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
