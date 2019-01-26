package io.realworld.test.expectation

import io.realworld.article.domain.ArticleReadRepository
import io.realworld.article.domain.Slug
import io.realworld.article.endpoint.ArticleConverter
import io.realworld.article.infrastructure.ArticleConfiguration
import io.realworld.article.query.ArticleQueryService
import org.assertj.core.api.Assertions.assertThat
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Component

@Component
@Import(ArticleConfiguration::class)
class ArticleExpectation(
        private val articleReadRepository: ArticleReadRepository,
        private val articleQueryService: ArticleQueryService,
        private val articleConverter: ArticleConverter
) {

    fun notExistsFor(slug: Slug) {
        assertThat(articleReadRepository.findBy(slug)).isNull()
    }

    fun existsFor(slug: Slug) {
        assertThat(articleReadRepository.findBy(slug)).isNotNull()
    }

    fun isFavorited(slug: Slug) {
        assertThat(getBy(slug).favorited).isTrue()
    }

    fun isNotFavorited(slug: Slug) {
        assertThat(getBy(slug).favorited).isFalse()
    }

    fun hasFavoriteCount(slug: Slug, count: Int) {
        assertThat(getBy(slug).favoritesCount).isEqualTo(count)
    }

    private fun getBy(slug: Slug) = articleQueryService.findBy(slug).let(articleConverter::toDto)
}
