package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.user.domain.User
import java.time.LocalDateTime

data class Article(
        val id: ArticleId = ArticleId.New,
        val slug: String,
        val title: String,
        val author: User,
        val createdAt: LocalDateTime = LocalDateTime.now()
//        val tags: List<Tag>
)
