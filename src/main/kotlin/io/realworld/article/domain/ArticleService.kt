package io.realworld.article.domain

import io.realworld.article.endpoint.CreateArticleDto
import io.realworld.article.endpoint.UpdateArticleDto
import io.realworld.comment.domain.CommentService
import io.realworld.security.domain.LoggedUserService
import io.realworld.shared.domain.ApplicationException
import org.springframework.dao.DuplicateKeyException
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
        try {
            return articleWriteRepository.create(article)
        } catch (ex: DuplicateKeyException) {
            throw DuplicateArticleException(article.slug)
        }
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

class DuplicateArticleException(slug: Slug) : ApplicationException("Duplicate article for slug ${slug.value}")
