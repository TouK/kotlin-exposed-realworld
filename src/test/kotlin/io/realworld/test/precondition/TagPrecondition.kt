package io.realworld.test.precondition

import io.realworld.article.domain.Tag
import io.realworld.article.domain.TagGen
import io.realworld.article.domain.TagWriteRepository
import io.realworld.article.infrastructure.TagTable
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
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TagTable.tableName)
    }
}
