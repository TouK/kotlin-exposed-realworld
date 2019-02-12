package io.realworld.comment.infrastructure

import io.realworld.article.domain.ArticleGen
import io.realworld.comment.domain.CommentGen
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
import org.assertj.core.api.Assertions.assertThat
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
            SqlCommentReadRepository::class,
            PreconditionConfiguration::class,
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
@Transactional
internal class SqlCommentReadRepositoryTest {

    @Autowired
    lateinit var given: Precondition

    @Autowired
    lateinit var commentReadRepository: SqlCommentReadRepository

    @Test
    fun `should load comments for article`() {
        val author = given.user.exists()
        val commenter = given.user.exists()
        val article = given.article.exist(ArticleGen.build(author))
        val comment = given.comment.exist(CommentGen.build(article = article, author = commenter))

        assertThat(commentReadRepository.findAllBy(article.id)).containsExactly(comment)
    }
}
