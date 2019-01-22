package io.realworld.comment.infrastructure

import io.realworld.article.domain.ArticleGen
import io.realworld.article.domain.ArticleWriteRepository
import io.realworld.article.infrastructure.ArticleConfiguration
import io.realworld.comment.domain.CommentGen
import io.realworld.shared.TestDataConfiguration
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.user.infrastructure.TestUserRepository
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
            ArticleConfiguration::class,
            TestDataConfiguration::class,
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
    lateinit var testUserRepository: TestUserRepository

    @Autowired
    lateinit var articleWriteRepository: ArticleWriteRepository

    @Autowired
    lateinit var testCommentRepository: TestCommentRepository

    @Autowired
    lateinit var sqlCommentRepository: SqlCommentReadRepository

    @Test
    fun `should load comments for article`() {
        val author = testUserRepository.insert()
        val commenter = testUserRepository.insert()
        val article = articleWriteRepository.create(ArticleGen.build(author))
        val comment = testCommentRepository.create(CommentGen.build(article = article, author = commenter))

        assertThat(sqlCommentRepository.findAllByArticleId(article.id)).containsExactly(comment)
    }
}
