package io.realworld.article.infrastructure

import io.realworld.article.domain.ArticleGen
import io.realworld.shared.BaseDatabaseTest
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
internal class SqlArticleReadRepositoryTest @Autowired constructor(
    val sqlArticleReadRepository: SqlArticleReadRepository, val given: Precondition
) : BaseDatabaseTest() {

    @Test
    fun `should find article by id`() {
        val author = given.user.exists()
        val tag = given.tag.exists()
        val article = given.article.exist(ArticleGen.build(author, listOf(tag)))

        val foundArticle = sqlArticleReadRepository.findBy(article.id)
        // created/updatedAt has different precision
        assertThat(foundArticle)
            .usingRecursiveComparison().ignoringFields("createdAt", "updatedAt")
            .isEqualTo(article)
    }
}
