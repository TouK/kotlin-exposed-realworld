package io.realworld.article.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.ArticleIdConverter
import io.realworld.shared.refs.UserId
import io.realworld.shared.refs.UserIdConverter
import java.time.ZonedDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "articles")
data class Article(

        @Id @GeneratedValue
        @Convert(converter = ArticleIdConverter::class)
        val id: ArticleId = ArticleId.New,

        val title: String,

        @Convert(converter = SlugConverter::class)
        val slug: Slug = Slug.fromTitle(title),

        val description: String,

        @Column(length = 1000)
        val body: String,

        @Convert(converter = UserIdConverter::class)
        @Column(name = "user_id")
        val authorId: UserId,

        @ManyToMany
        @JoinTable(name = "article_tags")
        val tags: List<Tag> = emptyList(),

        @Column(name = "created_at")
        val createdAt: ZonedDateTime = ZonedDateTime.now(),

        @Column(name = "updated_at")
        val updatedAt: ZonedDateTime = ZonedDateTime.now()
) {
    fun withTitle(title: String) = copy(title = title, slug = Slug.fromTitle(title))
}

data class Slug(
        val value: String
) {
    companion object {
        fun fromTitle(title: String) = Slug(title.lowercase().replace(Regex("\\W+"), "-"))
    }
}

class SlugConverter : AttributeConverter<Slug, String> {
    override fun convertToDatabaseColumn(attribute: Slug): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): Slug {
        return Slug(dbData)
    }

}
