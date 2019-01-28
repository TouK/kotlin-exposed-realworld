package io.realworld.article.endpoint

import io.realworld.article.domain.Tag
import io.realworld.article.query.ArticleQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(TagEndpoint.PATH)
class TagEndpoint(
        val articleQueryService: ArticleQueryService
) {

    companion object {
        const val PATH = "/tags"
    }

    @GetMapping
    fun list() = TagsResponse(tags = articleQueryService.findAllTags().map(Tag::name))
}
