package io.realworld.article.domain

import io.realworld.shared.Gen
import io.realworld.user.domain.User
import io.realworld.user.domain.UserGen

object ArticleGen {
    fun build(user : User = UserGen.build()) = Article(
            slug = Gen.alphanumeric(10),
            title = Gen.alphanumeric(),
            author = user
    )
}
