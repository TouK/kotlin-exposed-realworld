package io.realworld.comment.infrastructure

import io.realworld.comment.domain.Comment
import io.realworld.shared.infrastructure.getOrThrow
import org.jetbrains.exposed.sql.insert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Repository
@Transactional
class TestCommentRepository {

    fun create(comment: Comment) =
            CommentTable.insert {
                it.from(comment)
                it[CommentTable.updatedAt] = ZonedDateTime.now()
            }.getOrThrow(CommentTable.id).let { comment.copy(id = it) }
}
