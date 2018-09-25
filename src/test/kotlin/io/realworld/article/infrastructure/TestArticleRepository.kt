package io.realworld.article.infrastructure

import io.realworld.article.domain.Article
import org.jetbrains.exposed.sql.insert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class TestArticleRepository {

    fun insert(article: Article): Article {
        val articleId = ArticleTable.insert {
            it.from(article)
        }.get(ArticleTable.id)!!

        article.tags.forEach { tag ->
            ArticleTagTable.insert {
                it[ArticleTagTable.tagId] = tag.id
                it[ArticleTagTable.articleId] = articleId
            }
        }

        return article.copy(id = articleId)
    }
}
