package io.realworld.test.precondition

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleTable
import io.realworld.article.domain.ArticleTagsTable
import io.realworld.article.domain.ArticleWriteRepository
import io.realworld.comment.domain.CommentTable
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
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ArticleTagsTable.tableName)
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ArticleTable.tableName)
    }
}
