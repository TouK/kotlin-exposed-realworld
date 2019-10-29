package io.realworld.article.infrastructure

import io.realworld.article.domain.Tag
import io.realworld.article.domain.TagReadRepository
import io.realworld.article.domain.TagTable
import io.realworld.article.domain.TagWriteRepository
import io.realworld.article.domain.toTag
import io.realworld.shared.infrastructure.getOrThrow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class SqlTagReadRepository : TagReadRepository {

    override fun findAllByNames(names: List<String>) =
            TagTable.select { TagTable.name inList names }
                    .map { it.toTag() }

    override fun findAll() =
            TagTable.selectAll().map { it.toTag() }
}

@Component
class SqlTagWriteRepository : TagWriteRepository {

    override fun create(tag: Tag) =
        TagTable.insert { it[TagTable.name] = tag.name }
                .getOrThrow(TagTable.id)
                .let { tag.copy(id = it) }
}
