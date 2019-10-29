package io.realworld.test.precondition

import io.realworld.article.domain.ArticleTagsTable
import io.realworld.article.domain.Tag
import io.realworld.article.domain.TagGen
import io.realworld.article.domain.TagTable
import io.realworld.article.domain.TagWriteRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.test.jdbc.JdbcTestUtils

@Component
class TagPrecondition(
        private val tagWriteRepository: TagWriteRepository,
        private val jdbcTemplate: JdbcTemplate
) : Clearable {

    fun exists(tag: Tag = TagGen.build()) = tagWriteRepository.create(tag)

    override fun empty() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ArticleTagsTable.tableName)
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TagTable.tableName)
    }
}
