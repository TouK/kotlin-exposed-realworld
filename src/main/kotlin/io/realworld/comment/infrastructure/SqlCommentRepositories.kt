package io.realworld.comment.infrastructure

import io.realworld.article.infrastructure.ArticleTable
import io.realworld.article.infrastructure.articleId
import io.realworld.comment.domain.Comment
import io.realworld.comment.domain.CommentReadRepository
import io.realworld.comment.domain.CommentWriteRepository
import io.realworld.shared.infrastructure.getOrThrow
import io.realworld.shared.infrastructure.longWrapper
import io.realworld.shared.infrastructure.selectSingleOrNull
import io.realworld.shared.infrastructure.zonedDateTime
import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.CommentId
import io.realworld.user.infrastructure.UserTable
import io.realworld.user.infrastructure.userId
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.springframework.stereotype.Component

object CommentTable : Table("comments") {
    val id = commentId("id").primaryKey().autoIncrement()
    val userId = userId("user_id").references(UserTable.id)
    val articleId = articleId("article_id").references(ArticleTable.id)
    val body = text("body")
    val createdAt = zonedDateTime("created_at")
    val updatedAt = zonedDateTime("updated_at")
}

@Component
class SqlCommentReadRepository : CommentReadRepository {

    override fun findBy(commentId: CommentId) =
            CommentTable.selectSingleOrNull { CommentTable.id eq commentId }?.toComment()

    override fun findAllBy(articleId: ArticleId) =
            CommentTable
                    .select { CommentTable.articleId eq articleId }
                    .map { it.toComment() }
    }

@Component
class SqlCommentWriteRepository : CommentWriteRepository {

    override fun create(comment: Comment) =
            CommentTable.insert { it.from(comment) }
                    .getOrThrow(CommentTable.id)
                    .let { comment.copy(id = it) }

    override fun delete(commentId: CommentId) {
        CommentTable.deleteWhere { CommentTable.id eq commentId }
    }

    override fun deleteAllFor(articleId: ArticleId) {
        CommentTable.deleteWhere { CommentTable.articleId eq articleId }
    }
}

fun ResultRow.toComment() = Comment(
        id = this[CommentTable.id],
        authorId = this[CommentTable.userId],
        articleId = this[CommentTable.articleId],
        body = this[CommentTable.body],
        createdAt = this[CommentTable.createdAt],
        updatedAt = this[CommentTable.updatedAt]
)

fun UpdateBuilder<Any>.from(comment: Comment) {
    this[CommentTable.articleId] = comment.articleId
    this[CommentTable.body] = comment.body
    this[CommentTable.userId] = comment.authorId
    this[CommentTable.createdAt] = comment.createdAt
    this[CommentTable.updatedAt] = comment.updatedAt
}

fun Table.commentId(name: String) = longWrapper<CommentId>(name, CommentId::Persisted, CommentId::value)
