package io.realworld.article.domain

import io.realworld.shared.Gen
import io.realworld.user.domain.Author

object ArticleGen {

    fun build(author: Author, tags: List<Tag> = emptyList()) = Article(
            title = Gen.alphanumeric(),
            description = Gen.alphanumeric(100),
            body = Gen.alphanumeric(1000),
            authorId = author.id,
            tags = tags
    )
}

object TagGen {
    fun build(name: String = Gen.alphanumeric()) = Tag(name = name)
}
