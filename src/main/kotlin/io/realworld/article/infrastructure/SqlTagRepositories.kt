package io.realworld.article.infrastructure

import io.realworld.article.domain.Tag
import io.realworld.article.domain.TagReadRepository
import io.realworld.article.domain.TagTable
import io.realworld.article.domain.TagWriteRepository
import io.realworld.article.domain.insert
import io.realworld.article.domain.toTagList
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class SqlTagReadRepository : TagReadRepository {

    override fun findAllByNames(names: List<String>) =
            TagTable.select { TagTable.name inList names }
                    .toTagList()

    override fun findAll() =
            TagTable.selectAll().toTagList()
}

@Component
class SqlTagWriteRepository : TagWriteRepository {
    override fun create(tag: Tag) = TagTable.insert(tag)
}
