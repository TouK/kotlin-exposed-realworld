package io.realworld.test.precondition

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleWriteRepository
import io.realworld.article.infrastructure.ArticleTable
import io.realworld.article.infrastructure.ArticleTagTable
import io.realworld.comment.infrastructure.CommentTable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.test.jdbc.JdbcTestUtils

@Component
class ArticlePrecondition(
        private val articleWriteRepository: ArticleWriteRepository,
        private val jdbcTemplate: JdbcTemplate
): Clearable {

    fun exist(article: Article) = articleWriteRepository.create(article)

    override fun empty() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, CommentTable.tableName)
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ArticleTagTable.tableName)
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ArticleTable.tableName)
    }
}
