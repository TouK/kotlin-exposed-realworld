package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId

interface ArticleReadRepository {

    fun findAll(): List<Article>
    fun findBy(articleId: ArticleId): Article?
    fun findBy(slug: String): Article?

    fun getBy(slug: String) = findBy(slug) ?: throw ArticleNotFoundException(slug)
}

interface ArticleWriteRepository {
    fun create(article: Article): Article
}
