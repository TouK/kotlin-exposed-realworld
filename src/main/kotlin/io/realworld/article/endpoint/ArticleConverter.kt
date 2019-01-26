package io.realworld.article.endpoint

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleFavoriteReadRepository
import io.realworld.security.domain.LoggedUserService
import io.realworld.user.endpoint.toDto
import io.realworld.user.query.UserQueryService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class ArticleConverter(
        private val articleFavoriteReadRepository: ArticleFavoriteReadRepository,
        private val userQueryService: UserQueryService,
        private val loggedUserService: LoggedUserService
) {

    fun toDto(article: Article): ArticleDto {
        val favoritedBy = articleFavoriteReadRepository.findBy(article.id)
        val loggedUser = loggedUserService.loggedUser()
        val author = userQueryService.findBy(article.authorId)
        val favorited = loggedUser?.let { favoritedBy.contains(it.id) } ?: false
        return article.toDto(author.toDto(), favorited, favoritedBy.count())
    }
}
