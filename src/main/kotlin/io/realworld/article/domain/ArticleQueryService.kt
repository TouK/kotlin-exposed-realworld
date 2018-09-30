package io.realworld.article.domain

import io.realworld.article.endpoint.ArticleDto
import io.realworld.article.endpoint.toDto
import io.realworld.user.domain.LoggedUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ArticleQueryService(
        private val articleReadRepository: ArticleReadRepository,
        private val articleFavoriteReadRepository: ArticleFavoriteReadRepository,
        private val loggedUserService: LoggedUserService
) {

    fun findBy(slug: String): ArticleDto {
        val article = articleReadRepository.getBy(slug)
        val favoritedBy = articleFavoriteReadRepository.findBy(article.id)
        val loggedUser = loggedUserService.loggedUser()
        return article.toDto(favoritedBy.contains(loggedUser.id), favoritedBy.count())
    }
}
