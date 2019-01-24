package io.realworld.article.endpoint

import io.realworld.article.domain.ArticleService
import io.realworld.article.domain.Slug
import io.realworld.article.query.ArticleQueryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(ArticleEndpoint.PATH)
class ArticleEndpoint(
        private val articleQueryService: ArticleQueryService,
        private val articleService: ArticleService
) {

    companion object {
        const val PATH = "/articles"
        const val SLUG_PARAM = "slug"
        const val FEED_PATH = "/feed"
    }

    @GetMapping
    fun list() = ArticlesResponse(articleQueryService.findAll())

    @GetMapping(FEED_PATH)
    fun feed() = ArticlesResponse(articleQueryService.findAllOrderByMostRecent())

    @GetMapping("/{$SLUG_PARAM}")
    fun get(@PathVariable(name = SLUG_PARAM, required = true) slug: Slug) =
            ArticleResponse(articleQueryService.findBy(slug))

    @PostMapping
    fun create(@Valid @RequestBody request: CreateArticleRequest) =
            ArticleResponse(articleService.create(request.article))

    @PutMapping("/{$SLUG_PARAM}")
    fun update(@PathVariable(name = SLUG_PARAM, required = true) slug: Slug, @Valid @RequestBody request: UpdateArticleRequest) =
            ArticleResponse(articleService.update(slug, request.article))

    @DeleteMapping("/{$SLUG_PARAM}")
    fun delete(@PathVariable(name = SLUG_PARAM, required = true) slug: Slug) {
        articleService.delete(slug)
    }
}
