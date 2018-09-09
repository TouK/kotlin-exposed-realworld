package io.realworld.comment.infrastructure

import io.realworld.article.infrastructure.ArticleTable
import io.realworld.article.infrastructure.articleId
import io.realworld.comment.domain.Comment
import io.realworld.comment.domain.CommentRepository
import io.realworld.shared.infrastructure.localDateTime
import io.realworld.shared.infrastructure.longWrapper
import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.CommentId
import io.realworld.user.infrastructure.UserTable
import io.realworld.user.infrastructure.toUser
import io.realworld.user.infrastructure.userId
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.springframework.stereotype.Component

object CommentTable : Table("comments") {
    val id = commentId("id").primaryKey().autoIncrement()
    val userId = userId("user_id").references(UserTable.id)
    val articleId = articleId("article_id").references(ArticleTable.id)
    val createdAt = localDateTime("created_at")
    val updatedAt = localDateTime("updated_at")
}

@Component
class SqlCommentRepository : CommentRepository {
    override fun findAllByArticleId(articleId: ArticleId) =
            (CommentTable innerJoin UserTable)
                    .select { CommentTable.articleId eq articleId }
                    .map { it.toComment() }
    }

fun ResultRow.toComment() = Comment(
        id = this[CommentTable.id],
        author = this.toUser(CommentTable.userId),
        articleId = this[CommentTable.articleId],
        createdAt = this[CommentTable.createdAt]
)

fun UpdateBuilder<Any>.from(comment: Comment) {
    this[CommentTable.articleId] = comment.articleId
    this[CommentTable.userId] = comment.author.id
    this[CommentTable.createdAt] = comment.createdAt
}

fun Table.commentId(name: String) = longWrapper<CommentId>(name, CommentId::Persisted, CommentId::value)
