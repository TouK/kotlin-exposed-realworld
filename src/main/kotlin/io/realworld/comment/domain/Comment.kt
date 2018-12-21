package io.realworld.comment.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.CommentId
import io.realworld.user.domain.User
import java.time.ZonedDateTime

data class Comment(
        val id: CommentId = CommentId.New,
        val articleId: ArticleId,
        val author: User,
        val createdAt: ZonedDateTime = ZonedDateTime.now()
)
