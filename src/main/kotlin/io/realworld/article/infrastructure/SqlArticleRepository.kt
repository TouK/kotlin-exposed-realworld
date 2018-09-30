package io.realworld.article.infrastructure

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleReadRepository
import io.realworld.shared.infrastructure.localDateTime
import io.realworld.shared.infrastructure.longWrapper
import io.realworld.shared.infrastructure.selectSingleOrNull
import io.realworld.shared.refs.ArticleId
import io.realworld.user.infrastructure.UserTable
import io.realworld.user.infrastructure.toUser
import io.realworld.user.infrastructure.userId
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.springframework.stereotype.Component

object ArticleTable : Table("articles") {
    val id = articleId("id").primaryKey().autoIncrement()
    val slug = text("slug")
    val title = text("title")
    val authorId = userId("user_id").references(UserTable.id)
    val description = text("description")
    val body = text("body")
    val createdAt = localDateTime("created_at")
    val updatedAt = localDateTime("updated_at")
}

object ArticleTagTable : Table("article_tags") {
    val tagId = tagId("tag_id").references(TagTable.id)
    val articleId = articleId("article_id").references(ArticleTable.id)
}

@Component
class SqlArticleRepository : ArticleReadRepository {
    override fun findBy(articleId: ArticleId) =
            (ArticleTable innerJoin ArticleTagTable innerJoin TagTable
                    innerJoin UserTable)
                    .select { ArticleTable.id eq articleId }
                    .toArticle()

    override fun findBy(slug: String) =
            (ArticleTable innerJoin UserTable)
                    .selectSingleOrNull { ArticleTable.slug eq slug }?.toArticle()
}

fun Iterable<ResultRow>.toArticle() = this.fold(this.first().toArticle()) { article, resultRow ->
        article.copy(tags = article.tags + resultRow.toTag())
    }

fun ResultRow.toArticle() = Article(
        id = this[ArticleTable.id],
        slug = this[ArticleTable.slug],
        title = this[ArticleTable.title],
        description = this[ArticleTable.description],
        body = this[ArticleTable.body],
        author = toUser(ArticleTable.authorId),
        createdAt = this[ArticleTable.createdAt],
        updatedAt = this[ArticleTable.updatedAt],
        tags = emptyList()
)

fun UpdateBuilder<Any>.from(article: Article) = this.run {
    this[ArticleTable.slug] = article.slug
    this[ArticleTable.title] = article.title
    this[ArticleTable.description] = article.description
    this[ArticleTable.body] = article.body
    this[ArticleTable.authorId] = article.author.id
    this[ArticleTable.createdAt] = article.createdAt
    this[ArticleTable.updatedAt] = article.updatedAt
}

fun Table.articleId(name: String) = longWrapper<ArticleId>(name, ArticleId::Persisted, ArticleId::value)
