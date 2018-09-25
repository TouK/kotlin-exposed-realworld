package io.realworld.article.endpoint

import io.realworld.article.domain.ArticleQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ArticleEndpoint.PATH)
class ArticleEndpoint(
        private val articleQueryRepository: ArticleQueryRepository
) {

    companion object {
        const val PATH = "/articles"
        const val SLUG_PARAM = "slug"
    }

    @GetMapping("/$SLUG_PARAM")
    fun get(@PathVariable(name = SLUG_PARAM, required = true) slug: String) =
            articleQueryRepository.getBy(slug).toDto()

}
