package io.realworld.comment.query

import io.realworld.article.domain.Slug
import io.realworld.article.query.ArticleQueryService
import io.realworld.comment.domain.Comment
import io.realworld.comment.domain.CommentReadRepository
import io.realworld.shared.refs.CommentId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentQueryService(
        private val commentReadRepository: CommentReadRepository,
        private val articleQueryService: ArticleQueryService
) {

    fun findAllBy(slug: Slug): List<Comment> {
        val article = articleQueryService.getBy(slug)
        return commentReadRepository.findAllBy(article.id)
    }

    fun getBy(commentId: CommentId) = commentReadRepository.getBy(commentId)
}
