package io.realworld.comment.endpoint

import io.realworld.comment.domain.Comment
import io.realworld.user.endpoint.toDto
import io.realworld.user.query.UserQueryService
import org.springframework.stereotype.Component

@Component
class CommentConverter(
        private val userQueryService: UserQueryService
) {
    fun toDto(comment: Comment): CommentDto {
        val author = userQueryService.findBy(comment.authorId)
        return comment.toDto(author.toDto())
    }
}
