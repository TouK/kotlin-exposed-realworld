package io.realworld.shared.infrastructure

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.FieldSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

fun Query.forUpdate(value: Boolean) = if (value) forUpdate() else this

fun FieldSet.selectSingleOrNull(forUpdate: Boolean = false, where: SqlExpressionBuilder.() -> Op<Boolean>) =
    select(where).forUpdate(forUpdate).singleOrNull()

fun <T : Table> T.updateExactlyOne(where: SqlExpressionBuilder.() -> Op<Boolean>, body: T.(UpdateStatement) -> Unit) =
    update(where = where, body = body).also {
        check(it == 1) { "Expected to update exactly one row, but was: $it" }
    }

fun <T : Comparable<T>> Table.latestOrNull(column: Expression<T>) =
    selectAll().limit(1, 0).orderBy(column, isAsc = false).firstOrNull()

fun <T : Comparable<T>> Table.latest(column: Expression<T>) =
    requireNotNull(latestOrNull(column))

fun <K : Any, C : Any> InsertStatement<K>.getOrThrow(column: Column<C>) = requireNotNull(get(column)) {
    "Missing value for column $column"
}

fun <T : Any> ResultRow.getOrThrow(column: Column<out T?>) : T = requireNotNull(get(column)) {
    "Missing value for column $column"
}
