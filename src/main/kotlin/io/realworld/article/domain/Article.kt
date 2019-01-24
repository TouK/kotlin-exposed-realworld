package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.UserId
import java.time.ZonedDateTime

data class Article(
        val id: ArticleId = ArticleId.New,
        val title: String,
        val slug: String = title.toSlug(),
        val description: String,
        val body: String,
        val authorId: UserId,
        val tags: List<Tag>,
        val createdAt: ZonedDateTime = ZonedDateTime.now(),
        val updatedAt: ZonedDateTime = ZonedDateTime.now()
) {
    fun withTitle(title: String) = copy(title = title, slug = title.toSlug())
}

private fun String.toSlug() = this.toLowerCase().replace(Regex("\\W+"), "-")
