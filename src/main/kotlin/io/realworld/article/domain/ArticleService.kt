package io.realworld.article.domain

import io.realworld.article.endpoint.ArticleDto
import io.realworld.article.endpoint.CreateArticleDto
import io.realworld.article.endpoint.UpdateArticleDto
import io.realworld.article.endpoint.toDto
import io.realworld.security.domain.LoggedUserService
import io.realworld.user.endpoint.toDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ArticleService(
        private val articleReadRepository: ArticleReadRepository,
        private val articleWriteRepository: ArticleWriteRepository,
        private val loggedUserService: LoggedUserService,
        private val tagService: TagService
) {

    fun create(createArticleDto: CreateArticleDto): ArticleDto {
        val loggedUser = loggedUserService.loggedUserOrThrow()
        val tags = tagService.storeOrLoad(createArticleDto.tagList)
        val article = createArticleDto.run {
            Article(title = this.title, description = this.description, authorId = loggedUser.id, tags = tags,
                    body = this.body)
        }
        return articleWriteRepository.create(article).toDto(loggedUser.toDto())
    }

    fun update(slug: Slug, updateArticleDto: UpdateArticleDto): ArticleDto {
        val article = articleReadRepository.getBy(slug)
        val updatedArticle = updateArticleDto.run {
            article.copy(description = description ?: article.description, body = body ?: article.body)
                    .withTitle(title ?: article.title)
        }
        return updatedArticle
                .apply(articleWriteRepository::save)
                .toDto(loggedUserService.loggedUserOrThrow().toDto())
    }

    fun delete(slug: Slug) {
        val article = articleReadRepository.getBy(slug)
        articleWriteRepository.delete(article)
    }
}
