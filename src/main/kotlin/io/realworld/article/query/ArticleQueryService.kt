package io.realworld.article.query

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleReadRepository
import io.realworld.article.domain.Slug
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ArticleQueryService(
        private val articleReadRepository: ArticleReadRepository
) {

    fun findAll() = articleReadRepository.findAll()

    fun findBy(slug: Slug) = articleReadRepository.getBy(slug)

    fun findAllOrderByMostRecent() = findAll().sortedByDescending(Article::updatedAt)
}
