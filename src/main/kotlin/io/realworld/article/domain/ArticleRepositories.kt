package io.realworld.article.domain

import io.realworld.shared.domain.ApplicationException
import io.realworld.shared.refs.ArticleId

interface ArticleReadRepository {
    fun findAll(): List<Article>
    fun findBy(articleId: ArticleId): Article?
    fun findBy(slug: Slug): Article?

    fun getBy(slug: Slug) = findBy(slug) ?: throw ArticleNotFoundException(slug)
}

interface ArticleWriteRepository {
    fun create(article: Article): Article
    fun save(article: Article)
    fun delete(articleId: ArticleId)
}

class ArticleNotFoundException(slug: Slug) : ApplicationException("Article for slug ${slug.value} not found")
