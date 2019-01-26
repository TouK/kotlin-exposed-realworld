package io.realworld.article.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.realworld.article.endpoint.UpdateArticleDto
import io.realworld.comment.domain.CommentService
import io.realworld.security.domain.LoggedUserService
import io.realworld.shared.Gen
import io.realworld.user.domain.UserGen
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ArticleServiceTest {

    private val articleReadRepository = mock<ArticleReadRepository>()
    private val articleWriteRepository = mock<ArticleWriteRepository>()
    private val commentService = mock<CommentService>()
    private val loggedUserService = mock<LoggedUserService>()
    private val tagService = mock<TagService>()

    private val user = UserGen.build()
    private val article = ArticleGen.build(author = user)

    private val articleService = ArticleService(
            articleReadRepository, articleWriteRepository, commentService, loggedUserService, tagService
    )

    @BeforeEach
    fun setup() {
        whenever(articleReadRepository.getBy(article.slug)).thenReturn(article)
        whenever(loggedUserService.loggedUserOrThrow()).thenReturn(user)
    }

    @Test
    fun `should update article`() {
        val updateArticleDto = UpdateArticleDto(
                title = Gen.alphanumeric(10), description = Gen.alphanumeric(20), body = Gen.alphanumeric(200)
        )
        val updatedArticle = articleService
                .update(article.slug, updateArticleDto)

        updatedArticle.run {
            assertThat(title).isEqualTo(updateArticleDto.title)
            assertThat(description).isEqualTo(updateArticleDto.description)
            assertThat(body).isEqualTo(updateArticleDto.body)
        }

    }

    @Test
    fun `should update article's title`() {
        val updateArticleDto = UpdateArticleDto(title = Gen.alphanumeric(10), description = null, body = null)
        val updatedArticle = articleService
                .update(article.slug, updateArticleDto)

        assertThat(updatedArticle.title).isEqualTo(updateArticleDto.title)
        assertThat(updatedArticle.description).isEqualTo(article.description)
        assertThat(updatedArticle.body).isEqualTo(article.body)
    }

    @Test
    fun `should update article's description`() {
        val updateArticleDto = UpdateArticleDto(title = null, description = Gen.alphanumeric(10), body = null)
        val updatedArticle = articleService
                .update(article.slug, updateArticleDto)

        assertThat(updatedArticle.description).isEqualTo(updateArticleDto.description)
        assertThat(updatedArticle.title).isEqualTo(article.title)
        assertThat(updatedArticle.body).isEqualTo(article.body)
    }

    @Test
    fun `should update article's body`() {
        val updateArticleDto = UpdateArticleDto(title = null, description = null, body = Gen.alphanumeric(200))
        val updatedArticle = articleService
                .update(article.slug, updateArticleDto)

        assertThat(updatedArticle.body).isEqualTo(updateArticleDto.body)
        assertThat(updatedArticle.title).isEqualTo(article.title)
        assertThat(updatedArticle.description).isEqualTo(article.description)
    }

}
