package io.realworld.shared.infrastructure

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.FieldSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.QueryAlias
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

//TODO: test
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

//TODO: custom exception
fun <T : Any> ResultRow.getOrThrow(column: Column<out T?>) : T = requireNotNull(get(column)) {
    "Missing value for column $column"
}

fun <C, T> Iterable<ResultRow>.mapEntitiesGroupedByColumn(column: Column<C>, mapper: List<ResultRow>.() -> T): List<T> =
    groupBy { it[column] }
        .mapValues { (_, groupedRows) -> groupedRows.mapper() }
        .values
        .toList()

fun <T> Iterable<ResultRow>.toSingleEntity(mapper: SingleEntityRows.() -> T): T =
    SingleEntityRows(first(), this).mapper()

data class SingleEntityRows(val row: ResultRow, val rows: Iterable<ResultRow>)

sealed class ColumnTransformer {
    abstract operator fun <T : Any?> get(column: Column<T>): Column<T>

    object Identity : ColumnTransformer() {
        override fun <T> get(column: Column<T>) = column
    }

    class Alias(private val queryAlias: QueryAlias) : ColumnTransformer() {
        override fun <T> get(column: Column<T>) = queryAlias[column]
    }
}
