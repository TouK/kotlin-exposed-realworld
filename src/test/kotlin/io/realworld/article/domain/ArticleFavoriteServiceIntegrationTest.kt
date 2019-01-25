package io.realworld.article.domain

import io.realworld.article.infrastructure.ArticleConfiguration
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.test.expectation.Expectation
import io.realworld.test.expectation.ExpectationConfiguration
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
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
            ExpectationConfiguration::class,
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
@Transactional
internal class ArticleFavoriteServiceIntegrationTest {

    @Autowired
    lateinit var given: Precondition

    @Autowired
    lateinit var then: Expectation

    @Autowired
    lateinit var articleFavoriteService: ArticleFavoriteService

    @Test
    fun `should mark article as favorite and unfavorite`() {
        val author = given.user.exists()
        given.user.loggedUser()
        val article = given.article.exist(ArticleGen.build(author))

        articleFavoriteService.favorite(article.slug)

        then.article.isFavorited(article.slug)
        then.article.hasFavoriteCount(article.slug, 1)

        articleFavoriteService.unfavorite(article.slug)

        then.article.isNotFavorited(article.slug)
        then.article.hasFavoriteCount(article.slug, 0)
    }
}
