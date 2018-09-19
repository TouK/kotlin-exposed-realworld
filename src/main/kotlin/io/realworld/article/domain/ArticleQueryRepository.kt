package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId

interface ArticleQueryRepository {
    fun findById(articleId: ArticleId): Article?
    fun findBySlug(slug: String): Article?

    fun getBySlug(slug: String) = findBySlug(slug) ?: throw ArticleNotFoundException()
}
