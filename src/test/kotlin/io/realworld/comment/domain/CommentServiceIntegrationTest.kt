package io.realworld.comment.domain

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleGen
import io.realworld.comment.endpoint.CreateCommentDto
import io.realworld.comment.infrastructure.CommentConfiguration
import io.realworld.shared.Gen
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.test.expectation.Expectation
import io.realworld.test.expectation.ExpectationConfiguration
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
import io.realworld.user.domain.LoggedUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        classes = [
            CommentConfiguration::class,
            PreconditionConfiguration::class,
            ExpectationConfiguration::class,
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
@Transactional
internal class CommentServiceIntegrationTest {

    @Autowired
    lateinit var commentService: CommentService

    @Autowired
    lateinit var given: Precondition

    @Autowired
    lateinit var then: Expectation

    lateinit var loggedUser: LoggedUser

    lateinit var article: Article

    @BeforeEach
    internal fun setUp() {
        loggedUser = given.user.loggedUser()
        val author = given.user.exists()
        article = given.article.exist(ArticleGen.build(author = author))
    }

    @Test
    fun `should create comment`() {
        val createCommentDto = CreateCommentDto(Gen.alphanumeric(300))
        val createdComment = commentService.create(article.slug, createCommentDto)

        then.comment.exists(createdComment.id)

        createdComment.run {
            assertThat(body).isEqualTo(createCommentDto.body)
            assertThat(authorId).isEqualTo(loggedUser.id)
            assertThat(articleId).isEqualTo(article.id)
        }
    }

    @Test
    fun `should delete comment`() {
        val comment = given.comment.exist(CommentGen.build(author = loggedUser, article = article))
        then.comment.exists(comment.id)

        commentService.delete(comment.id)

        then.comment.notExists(comment.id)
    }

    @Test
    fun `should delete comments for article`() {
        val commentAlpha = given.comment.exist(CommentGen.build(author = loggedUser, article = article))
        val commentBravo = given.comment.exist(CommentGen.build(author = loggedUser, article = article))

        commentService.deleteAllFor(article.id)

        then.comment.notExists(commentAlpha.id)
        then.comment.notExists(commentBravo.id)
    }
}
