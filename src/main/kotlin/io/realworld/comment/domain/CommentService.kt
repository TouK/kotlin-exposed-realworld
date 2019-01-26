package io.realworld.comment.domain

import io.realworld.article.domain.Slug
import io.realworld.article.query.ArticleQueryService
import io.realworld.comment.endpoint.CreateCommentDto
import io.realworld.security.domain.LoggedUserService
import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.CommentId
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CommentService(
        private val commentWriteRepository: CommentWriteRepository,
        private val articleQueryService: ArticleQueryService,
        private val loggedUserService: LoggedUserService
) {
    fun create(slug: Slug, createCommentDto: CreateCommentDto): Comment {
        val article = articleQueryService.findBy(slug)
        return commentWriteRepository.create(Comment(
                articleId = article.id,
                body = createCommentDto.body,
                authorId = loggedUserService.loggedUserOrThrow().id
        ))
    }

    fun delete(commentId: CommentId) {
        return commentWriteRepository.delete(commentId)
    }

    fun deleteAllFor(articleId: ArticleId) {
        return commentWriteRepository.deleteAllFor(articleId)
    }
}
