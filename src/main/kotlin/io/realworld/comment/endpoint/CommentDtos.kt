package io.realworld.comment.endpoint

import io.realworld.comment.domain.Comment
import io.realworld.shared.refs.CommentId
import io.realworld.user.endpoint.UserDto
import java.time.ZonedDateTime

data class CommentsResponse(
        val comments: List<CommentDto>
)

data class CommentResponse(
        val comment: CommentDto
)

data class CreateCommentRequest(
        val comment: CreateCommentDto
)

data class CreateCommentDto(
        val body: String
)

data class CommentDto(
        val id: CommentId,
        val createdAt: ZonedDateTime,
        val updatedAt: ZonedDateTime,
        val body: String,
        val author: UserDto
)

fun Comment.toDto(author: UserDto) = CommentDto(
        id = this.id,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        body = this.body,
        author = author
)
