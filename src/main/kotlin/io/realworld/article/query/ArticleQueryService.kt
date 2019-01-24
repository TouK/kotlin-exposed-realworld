package io.realworld.article.query

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleFavoriteReadRepositories
import io.realworld.article.domain.ArticleReadRepository
import io.realworld.article.domain.Slug
import io.realworld.article.endpoint.ArticleDto
import io.realworld.article.endpoint.toDto
import io.realworld.security.domain.LoggedUserService
import io.realworld.user.domain.User
import io.realworld.user.query.UserQueryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ArticleQueryService(
        private val articleReadRepository: ArticleReadRepository,
        private val articleFavoriteReadRepository: ArticleFavoriteReadRepositories,
        private val loggedUserService: LoggedUserService,
        private val userQueryService: UserQueryService
) {

    fun findAll(): List<ArticleDto> {
        val loggedUser = loggedUserService.loggedUser()
        return articleReadRepository.findAll().map { toDto(it, loggedUser) }
    }

    fun findBy(slug: Slug): ArticleDto {
        val loggedUser = loggedUserService.loggedUser()
        val article = articleReadRepository.getBy(slug)
        return toDto(article, loggedUser)
    }

    fun findAllOrderByMostRecent() = findAll().sortedByDescending(ArticleDto::updatedAt)

    private fun toDto(article: Article, loggedUser: User?): ArticleDto {
        val favoritedBy = articleFavoriteReadRepository.findBy(article.id)
        val author = userQueryService.findBy(article.authorId)
        val favorited = loggedUser?.let { favoritedBy.contains(it.id) } ?: false
        return article.toDto(author, favorited, favoritedBy.count())
    }
}
