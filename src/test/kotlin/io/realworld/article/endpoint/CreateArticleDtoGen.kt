package io.realworld.article.endpoint

import io.realworld.shared.Gen

object CreateArticleDtoGen {

    fun build(tags: List<String> = emptyList()) = CreateArticleDto(
            title = Gen.alphanumeric(),
            description = Gen.alphanumeric(100),
            body = Gen.alphanumeric(1000),
            tagList = tags
    )
}
