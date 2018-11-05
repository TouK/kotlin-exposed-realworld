package io.realworld.article.domain

import io.realworld.shared.Gen
import io.realworld.user.domain.User

object ArticleGen {
    fun build(user: User, tags: List<Tag> = emptyList()) = Article(
            slug = Gen.alphanumeric(10),
            title = Gen.alphanumeric(),
            description = Gen.alphanumeric(100),
            body = Gen.alphanumeric(1000),
            authorId = user.id,
            tags = tags
    )
}

object TagGen {
    fun build(name: String = Gen.alphanumeric()) = Tag(name = name)
}
