package io.realworld.test.precondition

import io.realworld.comment.domain.Comment
import io.realworld.comment.domain.CommentWriteRepository
import org.springframework.stereotype.Component

@Component
class CommentPrecondition(
        private val commentWriteRepository: CommentWriteRepository
) {
    fun exist(comment: Comment) = commentWriteRepository.create(comment)
}
