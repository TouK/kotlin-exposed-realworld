package io.realworld.comment.domain

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleGen
import io.realworld.user.domain.User
import io.realworld.user.domain.UserGen

object CommentGen {
    fun build(article: Article = ArticleGen.build(), author: User = UserGen.build()) = Comment(
            articleId = article.id,
            author = author
    )
}
