package io.realworld.comment.domain

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleGen
import io.realworld.user.domain.User

object CommentGen {
    fun build(author: User, article: Article = ArticleGen.build(author, emptyList())) = Comment(
            articleId = article.id,
            author = author
    )
}
