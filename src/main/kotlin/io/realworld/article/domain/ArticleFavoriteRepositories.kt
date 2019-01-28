package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.UserId

interface ArticleFavoriteReadRepository {
    fun findBy(articleId: ArticleId): List<UserId>
}

interface ArticleFavoriteWriteRepository {
    fun create(articleId: ArticleId, userId: UserId)
    fun delete(articleId: ArticleId, userId: UserId)
}
