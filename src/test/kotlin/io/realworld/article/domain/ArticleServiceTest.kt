package io.realworld.article.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.realworld.article.endpoint.UpdateArticleDto
import io.realworld.security.domain.LoggedUserService
import io.realworld.shared.Gen
import io.realworld.user.domain.UserGen
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ArticleServiceTest {

    val articleReadRepository = mock<ArticleReadRepository>()
    val articleWriteRepository = mock<ArticleWriteRepository>()
    val loggedUserService = mock<LoggedUserService>()
    val tagService = mock<TagService>()

    val user = UserGen.build()
    val article = ArticleGen.build(user = user)

    val articleService = ArticleService(articleReadRepository, articleWriteRepository, loggedUserService, tagService)

    @BeforeEach
    fun setup() {
        whenever(articleReadRepository.getBy(article.slug)).thenReturn(article)
        whenever(loggedUserService.loggedUserOrThrow()).thenReturn(user)
    }

    @Test
    fun `should update article`() {
        val newBody = Gen.alphanumeric(200)
        val newTitle = Gen.alphanumeric(10)
        val newDescription = Gen.alphanumeric(20)

        val updatedArticle = articleService
                .update(article.slug, UpdateArticleDto(title = newTitle, description = newDescription, body = newBody))

        assertThat(updatedArticle.title).isEqualTo(newTitle)
        assertThat(updatedArticle.description).isEqualTo(newDescription)
        assertThat(updatedArticle.body).isEqualTo(newBody)
    }

    @Test
    fun `should update article's title`() {
        val newTitle = Gen.alphanumeric(10)

        val updatedArticle = articleService
                .update(article.slug, UpdateArticleDto(title = newTitle, description = null, body = null))

        assertThat(updatedArticle.title).isEqualTo(newTitle)
        assertThat(updatedArticle.description).isEqualTo(article.description)
        assertThat(updatedArticle.body).isEqualTo(article.body)
    }

    @Test
    fun `should update article's description`() {
        val newDescription = Gen.alphanumeric(10)

        val updatedArticle = articleService
                .update(article.slug, UpdateArticleDto(title = null, description = newDescription, body = null))

        assertThat(updatedArticle.description).isEqualTo(newDescription)
        assertThat(updatedArticle.title).isEqualTo(article.title)
        assertThat(updatedArticle.body).isEqualTo(article.body)
    }

    @Test
    fun `should update article's body`() {
        val newBody = Gen.alphanumeric(200)

        val updatedArticle = articleService
                .update(article.slug, UpdateArticleDto(title = null, description = null, body = newBody))

        assertThat(updatedArticle.body).isEqualTo(newBody)
        assertThat(updatedArticle.title).isEqualTo(article.title)
        assertThat(updatedArticle.description).isEqualTo(article.description)
    }

}
