package io.realworld.article.domain

import io.realworld.shared.domain.ApplicationException

class ArticleNotFoundException(slug: String) : ApplicationException("Article for slug $slug not found")
