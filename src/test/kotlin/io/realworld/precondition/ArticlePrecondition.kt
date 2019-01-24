package io.realworld.precondition

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleWriteRepository
import org.springframework.stereotype.Component

@Component
class ArticlePrecondition(
        private val articleWriteRepository: ArticleWriteRepository
) {
    fun exist(article: Article) = articleWriteRepository.create(article)
}
