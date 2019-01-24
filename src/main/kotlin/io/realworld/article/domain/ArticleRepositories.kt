package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId

interface ArticleReadRepository {
    fun findAll(): List<Article>
    fun findBy(articleId: ArticleId): Article?
    fun findBy(slug: Slug): Article?

    fun getBy(slug: Slug) = findBy(slug) ?: throw ArticleNotFoundException(slug.toString())
}

interface ArticleWriteRepository {
    fun create(article: Article): Article
    fun save(article: Article)
    fun delete(article: Article)
}
