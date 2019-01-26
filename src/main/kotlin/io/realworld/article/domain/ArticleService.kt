package io.realworld.article.domain

import io.realworld.article.endpoint.CreateArticleDto
import io.realworld.article.endpoint.UpdateArticleDto
import io.realworld.comment.domain.CommentService
import io.realworld.security.domain.LoggedUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArticleService(
        private val articleReadRepository: ArticleReadRepository,
        private val articleWriteRepository: ArticleWriteRepository,
        private val commentService: CommentService,
        private val loggedUserService: LoggedUserService,
        private val tagService: TagService
) {

    fun create(createArticleDto: CreateArticleDto): Article {
        val loggedUser = loggedUserService.loggedUserOrThrow()
        val tags = tagService.storeOrLoad(createArticleDto.tagList)
        val article = createArticleDto.run {
            Article(title = this.title, description = this.description, authorId = loggedUser.id, tags = tags,
                    body = this.body)
        }
        return articleWriteRepository.create(article)
    }

    fun update(slug: Slug, updateArticleDto: UpdateArticleDto): Article {
        val article = articleReadRepository.getBy(slug)
        val updatedArticle = updateArticleDto.run {
            article.copy(description = description ?: article.description, body = body ?: article.body)
                    .withTitle(title ?: article.title)
        }
        return updatedArticle.also(articleWriteRepository::save)
    }

    fun delete(slug: Slug) {
        val article = articleReadRepository.getBy(slug)
        commentService.deleteAllFor(article.id)
        articleWriteRepository.delete(article.id)
    }
}
