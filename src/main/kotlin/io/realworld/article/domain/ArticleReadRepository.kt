package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId

interface ArticleReadRepository {
    fun findBy(articleId: ArticleId): Article?
    fun findBy(slug: String): Article?

    fun getBy(slug: String) = findBy(slug) ?: throw ArticleNotFoundException(slug)
}
