package io.realworld.article.infrastructure

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleReadRepository
import io.realworld.article.domain.ArticleWriteRepository
import io.realworld.article.domain.Slug
import io.realworld.article.domain.TagId
import io.realworld.shared.infrastructure.getOrThrow
import io.realworld.shared.infrastructure.longWrapper
import io.realworld.shared.infrastructure.stringWrapper
import io.realworld.shared.infrastructure.updateExactlyOne
import io.realworld.shared.infrastructure.zonedDateTime
import io.realworld.shared.refs.ArticleId
import io.realworld.user.infrastructure.UserTable
import io.realworld.user.infrastructure.userId
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.springframework.stereotype.Component

object ArticleTable : Table("articles") {
    val id = articleId("id").primaryKey().autoIncrement()
    val slug = slug("slug")
    val title = text("title")
    val authorId = userId("user_id").references(UserTable.id)
    val description = text("description")
    val body = text("body")
    val createdAt = zonedDateTime("created_at")
    val updatedAt = zonedDateTime("updated_at")
}

object ArticleTagTable : Table("article_tags") {
    val tagId = tagId("tag_id").references(TagTable.id)
    val articleId = articleId("article_id").references(ArticleTable.id)
}

@Component
class SqlArticleReadRepository : ArticleReadRepository {

    companion object {
        val ArticleWithTags = (ArticleTable leftJoin ArticleTagTable leftJoin TagTable)
    }

    override fun findAll() =
            ArticleWithTags
                    .selectAll()
                    .toArticles()

    override fun findBy(articleId: ArticleId) =
            ArticleWithTags
                    .select { ArticleTable.id eq articleId }
                    .toArticles()
                    .singleOrNull()

    override fun findBy(slug: Slug) =
            ArticleWithTags
                    .select { ArticleTable.slug eq slug }
                    .toArticles()
                    .singleOrNull()
}

@Component
class SqlArticleWriteRepository : ArticleWriteRepository {

    override fun create(article: Article): Article {
        val savedArticle = ArticleTable.insert { it.from(article) }
                .getOrThrow(ArticleTable.id)
                .let { article.copy(id = it) }
        savedArticle.tags.forEach { tag ->
            ArticleTagTable.insert {
                it[ArticleTagTable.tagId] = tag.id
                it[ArticleTagTable.articleId] = savedArticle.id
            }
        }
        return savedArticle
    }

    override fun save(article: Article) {
        ArticleTable.updateExactlyOne({ ArticleTable.id eq article.id }) { it.from(article) }
    }

    override fun delete(articleId: ArticleId) {
        ArticleTagTable.deleteWhere { ArticleTagTable.articleId eq articleId }
        ArticleTable.deleteWhere { ArticleTable.id eq articleId }
    }
}

fun Iterable<ResultRow>.toArticles(): List<Article> {
    return fold(mutableMapOf<ArticleId, Article>()) { map, resultRow ->
        val article = resultRow.toArticle()
        val tagId = resultRow.tryGet(ArticleTagTable.tagId)
        val tag = tagId?.let { resultRow.toTag() }
        val current = map.getOrDefault(article.id, article)
        map[article.id] = current.copy(tags = current.tags + listOfNotNull(tag))
        map
    }.values.toList()
}

fun ResultRow.toArticle() = Article(
        id = this[ArticleTable.id],
        title = this[ArticleTable.title],
        slug = this[ArticleTable.slug],
        description = this[ArticleTable.description],
        body = this[ArticleTable.body],
        authorId = this[ArticleTable.authorId],
        createdAt = this[ArticleTable.createdAt],
        updatedAt = this[ArticleTable.updatedAt],
        tags = emptyList()
)

fun UpdateBuilder<Any>.from(article: Article) = this.run {
    this[ArticleTable.slug] = article.slug
    this[ArticleTable.title] = article.title
    this[ArticleTable.description] = article.description
    this[ArticleTable.body] = article.body
    this[ArticleTable.authorId] = article.authorId
    this[ArticleTable.createdAt] = article.createdAt
    this[ArticleTable.updatedAt] = article.updatedAt
}

fun Table.articleId(name: String) = longWrapper<ArticleId>(name, ArticleId::Persisted, ArticleId::value)

fun Table.tagId(name: String) = longWrapper<TagId>(name, TagId::Persisted, TagId::value)

fun Table.slug(name: String) = stringWrapper(name, ::Slug, Slug::value)
