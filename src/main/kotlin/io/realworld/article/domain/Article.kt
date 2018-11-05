package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.UserId
import java.time.LocalDateTime

data class Article(
        val id: ArticleId = ArticleId.New,
        val slug: String,
        val title: String,
        val description: String,
        val body: String,
        val authorId: UserId,
        val tags: List<Tag>,
        val createdAt: LocalDateTime = LocalDateTime.now(),
        val updatedAt: LocalDateTime = LocalDateTime.now()
)
