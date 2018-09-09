package io.realworld.comment.domain

import io.realworld.shared.refs.ArticleId

interface CommentRepository {
    fun findAllByArticleId(articleId: ArticleId): List<Comment>
}
