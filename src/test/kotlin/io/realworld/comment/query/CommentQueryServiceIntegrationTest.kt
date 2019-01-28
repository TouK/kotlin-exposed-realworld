package io.realworld.comment.query

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleGen
import io.realworld.comment.domain.CommentGen
import io.realworld.comment.infrastructure.CommentConfiguration
import io.realworld.shared.TestTransactionConfiguration
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
internal class CommentQueryServiceIntegrationTest {

    @Autowired
    lateinit var commentQueryService: CommentQueryService

    @Autowired
    lateinit var given: Precondition

    lateinit var loggedUser: LoggedUser

    lateinit var article: Article

    @BeforeEach
    internal fun setUp() {
        loggedUser = given.user.loggedUser()
        val author = given.user.exists()
        article = given.article.exist(ArticleGen.build(author = author))
    }

    @Test
    fun `should find comments for article`() {
        val commentAlpha = given.comment.exist(CommentGen.build(author = loggedUser, article = article))
        val commentBravo = given.comment.exist(CommentGen.build(author = loggedUser, article = article))

        assertThat(commentQueryService.findAllBy(article.slug)).containsExactlyInAnyOrder(commentAlpha, commentBravo)
    }

    @Test
    fun `should find comment by id`() {
        val comment = given.comment.exist(CommentGen.build(author = loggedUser, article = article))

        assertThat(commentQueryService.getBy(comment.id)).isEqualTo(comment)
    }
}
