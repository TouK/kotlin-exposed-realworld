package io.realworld.article.domain

import io.realworld.security.domain.LoggedUserService
import io.realworld.user.domain.LoggedUser
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArticleFavoriteService(
        private val articleReadRepository: ArticleReadRepository,
        private val articleFavoriteWriteRepository: ArticleFavoriteWriteRepository,
        private val loggedUserService: LoggedUserService
) {

    fun favorite(slug: Slug) = processArticle(slug) { article, loggedUser ->
        article.also { articleFavoriteWriteRepository.addFor(article.id, loggedUser.id) }
    }

    fun unfavorite(slug: Slug) = processArticle(slug) { article, loggedUser ->
        article.also { articleFavoriteWriteRepository.removeFor(article.id, loggedUser.id) }
    }

    private fun processArticle(slug: Slug, body: (Article, LoggedUser) -> Article): Article {
        val article = articleReadRepository.getBy(slug)
        val loggedUser = loggedUserService.loggedUserOrThrow()
        return body(article, loggedUser)
    }
}
