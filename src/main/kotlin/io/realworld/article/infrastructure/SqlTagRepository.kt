package io.realworld.article.infrastructure

import io.realworld.article.domain.Tag
import io.realworld.article.domain.TagId
import io.realworld.shared.infrastructure.longWrapper
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object TagTable : Table("tags") {
    val id = tagId("id").primaryKey().autoIncrement()
    val name = text("name")
}

class SqlTagRepository {
}

fun ResultRow.toTag() = Tag(
        id = this[TagTable.id],
        name = this[TagTable.name]
)

fun Table.tagId(name: String) = longWrapper<TagId>(name, TagId::Persisted, TagId::value)
