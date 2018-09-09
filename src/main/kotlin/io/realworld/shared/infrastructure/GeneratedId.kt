package io.realworld.shared.infrastructure

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import kotlin.reflect.KProperty

abstract class GeneratedId<T : Comparable<T>> : Comparable<GeneratedId<T>> {
    abstract val value: T

    override fun compareTo(other: GeneratedId<T>) = value.compareTo(other.value)
}

class IdNotPersistedDelegate<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Nothing = throw IllegalStateException("Id not persisted yet")
}

abstract class TableWithGeneratedId<T : GeneratedId<U>, U : Comparable<U>>(name: String = "") : Table(name) {
    abstract val id: Column<T>

    fun latestOrNull() = latestOrNull(id)

    fun latest() = latest(id)
}
