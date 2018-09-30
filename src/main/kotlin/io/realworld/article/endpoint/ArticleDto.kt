package io.realworld.article.endpoint

import io.realworld.article.domain.Article
import io.realworld.article.domain.Tag
import io.realworld.user.endpoint.UserDto
import io.realworld.user.endpoint.toDto
import java.time.LocalDateTime

data class ArticleDto(
        val slug: String,
        val title: String,
        val description: String,
        val body: String,
        val tagList: List<String>,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val favorited: Boolean,
        val favoritesCount: Int,
        val author: UserDto
)

fun Article.toDto(favorited: Boolean, favoritesCount: Int) = ArticleDto(
        slug = this.slug,
        title = this.title,
        description = this.description,
        body = this.body,
        tagList = this.tags.toDto(),
        createdAt = this.createdAt,
        updatedAt = this.createdAt,
        favorited = favorited,
        favoritesCount = favoritesCount,
        author = this.author.toDto()
)

fun List<Tag>.toDto() = this.map(Tag::name)
