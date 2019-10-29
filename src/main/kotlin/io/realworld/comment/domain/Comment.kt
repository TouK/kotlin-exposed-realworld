package io.realworld.comment.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.ArticleIdConverter
import io.realworld.shared.refs.CommentId
import io.realworld.shared.refs.UserId
import io.realworld.shared.refs.UserIdConverter
import pl.touk.exposed.Convert
import pl.touk.exposed.Converter
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "comments")
data class Comment(

        @Id @GeneratedValue
        @Convert(value = CommentIdConverter::class)
        val id: CommentId = CommentId.New,

        @Convert(value = ArticleIdConverter::class)
        @Column(name = "article_id")

        val articleId: ArticleId,

        @Column(length = 400)
        val body: String,

        @Convert(value = UserIdConverter::class)
        @Column(name = "user_id")
        val authorId: UserId,

        @Column(name = "created_at")
        val createdAt: ZonedDateTime = ZonedDateTime.now(),

        @Column(name = "updated_at")
        val updatedAt: ZonedDateTime = ZonedDateTime.now()
)

class CommentIdConverter : Converter<CommentId, Long> {
    override fun convertToDatabaseColumn(attribute: CommentId): Long {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: Long): CommentId {
        return CommentId.Persisted(dbData)
    }
}
