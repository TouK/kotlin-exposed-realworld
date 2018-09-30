package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.UserId

interface ArticleFavoriteWriteRepository {
    fun addFor(articleId: ArticleId, userId: UserId)
    fun removeFor(articleId: ArticleId, userId: UserId)
}
