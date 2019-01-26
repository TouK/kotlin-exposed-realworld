package io.realworld.comment.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.CommentId
import io.realworld.shared.refs.UserId
import java.time.ZonedDateTime

data class Comment(
        val id: CommentId = CommentId.New,
        val articleId: ArticleId,
        val body: String,
        val authorId: UserId,
        val createdAt: ZonedDateTime = ZonedDateTime.now(),
        val updatedAt: ZonedDateTime = ZonedDateTime.now()
)
