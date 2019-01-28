package io.realworld.article.endpoint

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleFavoriteReadRepository
import io.realworld.security.domain.LoggedUserService
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.LoggedUser
import io.realworld.user.endpoint.UserConverter
import io.realworld.user.query.UserQueryService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class ArticleConverter(
        private val articleFavoriteReadRepository: ArticleFavoriteReadRepository,
        private val userQueryService: UserQueryService,
        private val loggedUserService: LoggedUserService,
        private val userConverter: UserConverter
) {
    fun toDto(article: Article): ArticleDto {
        val favoritedBy = articleFavoriteReadRepository.findBy(article.id)
        val loggedUser = loggedUserService.loggedUser()
        val author = userQueryService.getBy(article.authorId)
        return article.toDto(
                author = userConverter.toProfileDto(author, loggedUser),
                favorited = loggedUser.isIn(favoritedBy),
                favoritesCount = favoritedBy.count()
        )
    }

    private fun LoggedUser?.isIn(userIds: List<UserId>) = this?.let { userIds.contains(this.id) } ?: false
}
