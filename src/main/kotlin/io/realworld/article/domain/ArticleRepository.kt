package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId

interface ArticleRepository {
    fun findById(articleId: ArticleId) : Article?
}
