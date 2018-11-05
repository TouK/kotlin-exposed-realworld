package io.realworld.article.query

import io.realworld.article.domain.ArticleFavoriteReadRepository
import io.realworld.article.domain.ArticleReadRepository
import io.realworld.user.domain.LoggedUserService
import io.realworld.user.query.UserQueryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ArticleQueryService(
        private val articleReadRepository: ArticleReadRepository,
        private val articleFavoriteReadRepository: ArticleFavoriteReadRepository,
        private val loggedUserService: LoggedUserService,
        private val userQueryService: UserQueryService
) {

    fun findBy(slug: String): ArticleDto {
        val article = articleReadRepository.getBy(slug)
        val user = userQueryService.findBy(article.authorId)
        val favoritedBy = articleFavoriteReadRepository.findBy(article.id)
        val loggedUser = loggedUserService.loggedUser()
        return article.toDto(user, favoritedBy.contains(loggedUser.id), favoritedBy.count())
    }
}
