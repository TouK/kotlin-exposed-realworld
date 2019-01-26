package io.realworld.test.expectation

import io.realworld.comment.domain.CommentReadRepository
import io.realworld.comment.infrastructure.CommentConfiguration
import io.realworld.shared.refs.CommentId
import org.assertj.core.api.Assertions.assertThat
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Component

@Component
@Import(CommentConfiguration::class)
class CommentExpectation(
        private val commentReadRepository: CommentReadRepository
) {
    fun exists(commentId: CommentId) {
        assertThat(commentReadRepository.findBy(commentId)).isNotNull
    }

    fun notExists(commentId: CommentId) {
        assertThat(commentReadRepository.findBy(commentId)).isNull()
    }
}
