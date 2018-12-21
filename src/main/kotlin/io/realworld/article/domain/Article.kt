package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.UserId
import java.time.ZonedDateTime

data class Article(
        val id: ArticleId = ArticleId.New,
        val slug: String? = null,
        val title: String,
        val description: String,
        val body: String,
        val authorId: UserId,
        val tags: List<Tag>,
        val createdAt: ZonedDateTime = ZonedDateTime.now(),
        val updatedAt: ZonedDateTime = ZonedDateTime.now()
)
