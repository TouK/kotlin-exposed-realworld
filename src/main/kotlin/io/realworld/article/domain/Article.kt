package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.UserId
import java.time.ZonedDateTime

data class Article(
        val id: ArticleId = ArticleId.New,
        val title: String,
        val slug: Slug = Slug.fromTitle(title),
        val description: String,
        val body: String,
        val authorId: UserId,
        val tags: List<Tag>,
        val createdAt: ZonedDateTime = ZonedDateTime.now(),
        val updatedAt: ZonedDateTime = ZonedDateTime.now()
) {
    fun withTitle(title: String) = copy(title = title, slug = Slug.fromTitle(title))

    fun addTag(tag: Tag?) = copy(tags = this.tags + listOfNotNull(tag))
}

data class Slug(
        val value: String
) {
    companion object {
        fun fromTitle(title: String) = Slug(title.toLowerCase().replace(Regex("\\W+"), "-"))
    }
}
