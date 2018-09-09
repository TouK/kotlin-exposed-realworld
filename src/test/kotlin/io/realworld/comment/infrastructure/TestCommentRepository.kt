package io.realworld.comment.infrastructure

import io.realworld.comment.domain.Comment
import org.jetbrains.exposed.sql.insert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Repository
@Transactional
class TestCommentRepository {

    fun insert(comment: Comment) =
            CommentTable.insert {
                it.from(comment)
                it[CommentTable.updatedAt] = LocalDateTime.now()
            }[CommentTable.id]!!.let { comment.copy(id = it) }
}
