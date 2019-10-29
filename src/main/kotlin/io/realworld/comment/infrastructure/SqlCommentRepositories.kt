package io.realworld.comment.infrastructure

import io.realworld.comment.domain.Comment
import io.realworld.comment.domain.CommentReadRepository
import io.realworld.comment.domain.CommentTable
import io.realworld.comment.domain.CommentWriteRepository
import io.realworld.comment.domain.from
import io.realworld.comment.domain.toComment
import io.realworld.shared.infrastructure.getOrThrow
import io.realworld.shared.infrastructure.selectSingleOrNull
import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.CommentId
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Component

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
