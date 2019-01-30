package io.realworld.shared.infrastructure

import kotlin.reflect.KProperty

abstract class GeneratedId<T : Comparable<T>> : Comparable<GeneratedId<T>> {
    abstract val value: T

    override fun compareTo(other: GeneratedId<T>) = value.compareTo(other.value)
}

class IdNotPersistedDelegate<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Nothing = throw IllegalStateException("Id not persisted yet")
}
