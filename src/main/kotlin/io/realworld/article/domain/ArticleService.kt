package io.realworld.article.domain

import io.realworld.article.endpoint.ArticleDto
import io.realworld.article.endpoint.CreateArticleDto
import io.realworld.article.endpoint.toDto
import io.realworld.security.domain.LoggedUserService
import io.realworld.security.domain.UserNotAuthorizedException
import io.realworld.user.endpoint.toDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ArticleService(
        private val articleWriteRepository: ArticleWriteRepository,
        private val loggedUserService: LoggedUserService,
        private val tagService: TagService
) {

    fun create(createArticleDto: CreateArticleDto): ArticleDto {
        val loggedUser = loggedUserService.loggedUser() ?: throw UserNotAuthorizedException()
        val tags = tagService.storeOrLoad(createArticleDto.tagList)
        val article = createArticleDto.run {
            Article(title = this.title, description = this.description, authorId = loggedUser.id, tags = tags,
                    body = this.body)
        }
        return articleWriteRepository.save(article).toDto(loggedUser.toDto())
    }
}
