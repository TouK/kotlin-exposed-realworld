package io.realworld.comment.domain

import io.realworld.shared.domain.ApplicationException
import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.CommentId

interface CommentReadRepository {
    fun findBy(commentId: CommentId): Comment?
    fun findAllBy(articleId: ArticleId): List<Comment>

    fun getBy(commentId: CommentId) = findBy(commentId) ?: throw CommentNotFoundException(commentId)
}

interface CommentWriteRepository {
    fun create(comment: Comment): Comment
    fun delete(commentId: CommentId)
    fun deleteAllFor(articleId: ArticleId)
}

class CommentNotFoundException(commentId: CommentId) : ApplicationException("Comment with id ${commentId.value} not found")
