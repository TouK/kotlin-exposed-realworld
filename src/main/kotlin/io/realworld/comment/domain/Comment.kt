package io.realworld.comment.domain

import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.ArticleIdConverter
import io.realworld.shared.refs.CommentId
import io.realworld.shared.refs.UserId
import io.realworld.shared.refs.UserIdConverter
import java.time.ZonedDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "comments")
data class Comment(

        @Id @GeneratedValue
        @Convert(converter = CommentIdConverter::class)
        val id: CommentId = CommentId.New,

        @Convert(converter = ArticleIdConverter::class)
        @Column(name = "article_id")

        val articleId: ArticleId,

        @Column(length = 400)
        val body: String,

        @Convert(converter = UserIdConverter::class)
        @Column(name = "user_id")
        val authorId: UserId,

        @Column(name = "created_at")
        val createdAt: ZonedDateTime = ZonedDateTime.now(),

        @Column(name = "updated_at")
        val updatedAt: ZonedDateTime = ZonedDateTime.now()
)

class CommentIdConverter : AttributeConverter<CommentId, Long> {
    override fun convertToDatabaseColumn(attribute: CommentId): Long {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: Long): CommentId {
        return CommentId.Persisted(dbData)
    }
}
