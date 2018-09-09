package io.realworld.article.infrastructure

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleRepository
import io.realworld.shared.infrastructure.localDateTime
import io.realworld.shared.infrastructure.longWrapper
import io.realworld.shared.infrastructure.selectSingleOrNull
import io.realworld.shared.refs.ArticleId
import io.realworld.user.infrastructure.UserTable
import io.realworld.user.infrastructure.toUser
import io.realworld.user.infrastructure.userId
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.springframework.stereotype.Component

object ArticleTable : Table("articles") {
    val id = articleId("id").primaryKey().autoIncrement()
    val slug = text("slug")
    val title = text("title")
    val userId = userId("user_id").references(UserTable.id)
    val createdAt = localDateTime("created_at")
    val updatedAt = localDateTime("updated_at")
}

@Component
class SqlArticleRepository : ArticleRepository {
    override fun findById(articleId: ArticleId) =
            (ArticleTable innerJoin UserTable)
                    .selectSingleOrNull { ArticleTable.id eq articleId }?.toArticle()
}

fun ResultRow.toArticle() = Article(
        id = this[ArticleTable.id],
        slug = this[ArticleTable.slug],
        title = this[ArticleTable.title],
        author = toUser(ArticleTable.userId),
        createdAt = this[ArticleTable.createdAt]
)

fun UpdateBuilder<Any>.from(article: Article) = this.run {
    this[ArticleTable.slug] = article.slug
    this[ArticleTable.title] = article.title
    this[ArticleTable.userId] = article.author.id
    this[ArticleTable.createdAt] = article.createdAt
}

fun Table.articleId(name: String) = longWrapper<ArticleId>(name, ArticleId::Persisted, ArticleId::value)
