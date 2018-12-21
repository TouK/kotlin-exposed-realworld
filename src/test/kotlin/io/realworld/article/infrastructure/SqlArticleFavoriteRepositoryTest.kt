package io.realworld.article.infrastructure

import io.realworld.article.domain.ArticleFavoriteReadRepository
import io.realworld.article.domain.ArticleFavoriteWriteRepository
import io.realworld.article.domain.ArticleGen
import io.realworld.article.domain.ArticleWriteRepository
import io.realworld.shared.TestDataConfiguration
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.user.domain.UserGen
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
internal class SqlArticleFavoriteRepositoryTest {

    @Autowired
    lateinit var testUserRepository: TestUserRepository

    @Autowired
    lateinit var articleWriteRepository: ArticleWriteRepository

    @Autowired
    lateinit var articleFavoriteWriteRepository: ArticleFavoriteWriteRepository

    @Autowired
    lateinit var articleFavoriteReadRepository: ArticleFavoriteReadRepository

    @Test
    fun `should favorite and unfavorite article`() {
        val author = testUserRepository.insert(UserGen.build())
        val article = articleWriteRepository.save(ArticleGen.build(author))
        val user = testUserRepository.insert(UserGen.build())

        articleFavoriteWriteRepository.addFor(article.id, user.id)

        assertThat(articleFavoriteReadRepository.findBy(article.id)).containsExactly(user.id)

        articleFavoriteWriteRepository.removeFor(article.id, user.id)

        assertThat(articleFavoriteReadRepository.findBy(article.id)).isEmpty()
    }
}
