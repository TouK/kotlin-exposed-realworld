package io.realworld.article.query

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleReadRepository
import io.realworld.article.domain.Slug
import io.realworld.article.domain.TagReadRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ArticleQueryService(
        private val articleReadRepository: ArticleReadRepository,
        private val tagReadRepository: TagReadRepository
) {

    fun getBy(slug: Slug) = articleReadRepository.getBy(slug)

    fun findAll() = articleReadRepository.findAll()

    fun findAllTags() = tagReadRepository.findAll()

    fun findAllOrderByMostRecent() = findAll().sortedByDescending(Article::updatedAt)
}
