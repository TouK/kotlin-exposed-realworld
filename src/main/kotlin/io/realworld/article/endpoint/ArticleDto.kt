package io.realworld.article.endpoint

import io.realworld.article.domain.Article
import io.realworld.article.domain.Tag
import io.realworld.user.endpoint.UserDto
import java.time.ZonedDateTime

data class ArticlesResponse(
        val articles: List<ArticleDto>,
        val articlesCount: Int
) {
    constructor(articles: List<ArticleDto>) : this(articles, articles.size)
}

data class ArticleResponse(
        val article: ArticleDto
)

data class ArticleDto(
        val slug: String?,
        val title: String,
        val description: String,
        val body: String,
        val tagList: List<String>,
        val createdAt: ZonedDateTime,
        val updatedAt: ZonedDateTime,
        val favorited: Boolean,
        val favoritesCount: Int,
        val author: UserDto
)

data class CreateArticleRequest(
        val article: CreateArticleDto
)

data class CreateArticleDto(
        val title: String,
        val description: String,
        val body: String,
        val tagList: List<String>
)

data class UpdateArticleRequest(
        val article: UpdateArticleDto
)

data class UpdateArticleDto(
        val title: String?,
        val description: String?,
        val body: String?
)

fun Article.toDto(author: UserDto, favorited: Boolean = false, favoritesCount: Int = 0) = ArticleDto(
        slug = this.slug.value,
        title = this.title,
        description = this.description,
        body = this.body,
        tagList = this.tags.toDto(),
        createdAt = this.createdAt,
        updatedAt = this.createdAt,
        favorited = favorited,
        favoritesCount = favoritesCount,
        author = author
)

fun List<Tag>.toDto() = this.map(Tag::name)
