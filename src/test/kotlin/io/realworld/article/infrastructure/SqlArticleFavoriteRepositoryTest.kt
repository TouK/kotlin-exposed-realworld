package io.realworld.article.infrastructure

import io.realworld.article.domain.ArticleFavoriteReadRepositories
import io.realworld.article.domain.ArticleFavoriteWriteRepository
import io.realworld.article.domain.ArticleGen
import io.realworld.article.domain.ArticleWriteRepository
import io.realworld.precondition.Precondition
import io.realworld.shared.PreconditionConfiguration
import io.realworld.shared.TestTransactionConfiguration
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
            PreconditionConfiguration::class,
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
    lateinit var given: Precondition

    @Autowired
    lateinit var articleWriteRepository: ArticleWriteRepository

    @Autowired
    lateinit var articleFavoriteWriteRepository: ArticleFavoriteWriteRepository

    @Autowired
    lateinit var articleFavoriteReadRepository: ArticleFavoriteReadRepositories

    @Test
    fun `should favorite and unfavorite article`() {
        val author = given.user.exists()
        val article = articleWriteRepository.create(ArticleGen.build(author))
        val user = given.user.exists()

        articleFavoriteWriteRepository.addFor(article.id, user.id)

        assertThat(articleFavoriteReadRepository.findBy(article.id)).containsExactly(user.id)

        articleFavoriteWriteRepository.removeFor(article.id, user.id)

        assertThat(articleFavoriteReadRepository.findBy(article.id)).isEmpty()
    }
}
