package io.realworld.comment.domain

import io.realworld.shared.refs.ArticleId

interface CommentReadRepository {
    fun findAllByArticleId(articleId: ArticleId): List<Comment>
}

interface CommentWriteRepository {
    fun create(comment: Comment): Comment
}
