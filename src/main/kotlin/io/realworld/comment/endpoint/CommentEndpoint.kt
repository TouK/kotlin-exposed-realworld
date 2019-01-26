package io.realworld.comment.endpoint

import io.realworld.article.domain.Slug
import io.realworld.article.endpoint.ArticleEndpoint
import io.realworld.comment.domain.CommentService
import io.realworld.comment.query.CommentQueryService
import io.realworld.shared.refs.CommentId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("${ArticleEndpoint.PATH}/{${ArticleEndpoint.SLUG_PARAM}}/${CommentEndpoint.COMMENTS_PATH}")
class CommentEndpoint(
        private val commentService: CommentService,
        private val commentQueryService: CommentQueryService,
        private val commentConverter: CommentConverter
) {

    companion object {
        const val COMMENTS_PATH = "/comments"
        const val COMMENT_ID_PARAM = "commentId"
    }

    @GetMapping("/{$COMMENT_ID_PARAM}")
    fun get(@PathVariable(name = CommentEndpoint.COMMENT_ID_PARAM, required = true) commentId: CommentId) =
            CommentResponse(commentQueryService.getBy(commentId).let(commentConverter::toDto))

    @GetMapping
    fun list(@PathVariable(name = ArticleEndpoint.SLUG_PARAM, required = true) slug: Slug) =
            CommentsResponse(commentQueryService.findAllBy(slug).map(commentConverter::toDto))

    @PostMapping
    fun create(@PathVariable(name = ArticleEndpoint.SLUG_PARAM, required = true) slug: Slug,
               @Valid @RequestBody request: CreateCommentRequest) =
            CommentResponse(commentService.create(slug, request.comment).let(commentConverter::toDto))

    @DeleteMapping("/{$COMMENT_ID_PARAM}")
    fun delete(@PathVariable(name = CommentEndpoint.COMMENT_ID_PARAM, required = true) commentId: CommentId) {
        commentService.delete(commentId)
    }
}
