package io.realworld.article.endpoint

import io.realworld.article.domain.ArticleFavoriteService
import io.realworld.article.domain.Slug
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${ArticleEndpoint.PATH}/{${ArticleEndpoint.SLUG_PARAM}}/${ArticleFavoriteEndpoint.FAVORITE_PATH}")
class ArticleFavoriteEndpoint(
        private val articleFavoriteService: ArticleFavoriteService,
        private val articleConverter: ArticleConverter
) {

    companion object {
        const val FAVORITE_PATH = "/favorite"
    }

    @PostMapping
    fun favorite(@PathVariable(name = ArticleEndpoint.SLUG_PARAM, required = true) slug: Slug) =
            ArticleResponse(articleFavoriteService.favorite(slug).let(articleConverter::toDto))

    @DeleteMapping
    fun unfavorite(@PathVariable(name = ArticleEndpoint.SLUG_PARAM, required = true) slug: Slug) =
            ArticleResponse(articleFavoriteService.unfavorite(slug).let(articleConverter::toDto))
}
