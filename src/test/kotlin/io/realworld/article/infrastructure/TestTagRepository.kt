package io.realworld.article.infrastructure

import io.realworld.article.domain.Tag
import io.realworld.article.domain.TagGen
import org.jetbrains.exposed.sql.insert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class TestTagRepository {

    fun insert(tag: Tag = TagGen.build()) =
            TagTable.insert {
                it[TagTable.name] = tag.name
            }.get(TagTable.id)!!.let { tag.copy(id = it) }
}
