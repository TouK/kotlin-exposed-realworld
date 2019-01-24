package io.realworld.test.expectation

import io.realworld.article.domain.ArticleReadRepository
import io.realworld.article.domain.Slug
import org.assertj.core.api.Assertions.assertThat
import org.springframework.stereotype.Component

@Component
class ArticleExpectation(
        private val articleReadRepository: ArticleReadRepository
) {
    fun notExistsFor(slug: Slug) {
        assertThat(articleReadRepository.findBy(slug)).isNull()
    }

    fun existsFor(slug: Slug) {
        assertThat(articleReadRepository.findBy(slug)).isNotNull()
    }
}
