package io.realworld.shared.infrastructure

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.FieldSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
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

fun <K : Any, C : Any> InsertStatement<K>.getOrThrow(column: Column<C>) = requireNotNull(get(column)) {
    "Missing value for column $column"
}
