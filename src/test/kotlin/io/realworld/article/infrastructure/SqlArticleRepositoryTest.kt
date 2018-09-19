package io.realworld.article.infrastructure

import io.realworld.article.domain.ArticleGen
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
            SqlArticleRepository::class,
            TestDataConfiguration::class,
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
@Transactional
internal class SqlArticleRepositoryTest {

    @Autowired
    lateinit var testUserRepository: TestUserRepository

    @Autowired
    lateinit var testTagRepository: TestTagRepository

    @Autowired
    lateinit var testArticleRepository: TestArticleRepository

    @Autowired
    lateinit var sqlArticleRepository: SqlArticleRepository

    @Test
    fun `should find article by id`() {
        val author = testUserRepository.insert()
        val tag = testTagRepository.insert()
        val article = testArticleRepository.insert(ArticleGen.build(author, listOf(tag)))

        assertThat(sqlArticleRepository.findById(article.id)).isEqualTo(article)
    }

}
