package io.realworld.article.infrastructure

import io.realworld.article.domain.ArticleGen
import io.realworld.article.domain.TagGen
import io.realworld.article.domain.TagWriteRepository
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
internal class SqlArticleReadRepositoryTest {

    @Autowired
    lateinit var given: Precondition

    @Autowired
    lateinit var tagWriteRepository: TagWriteRepository

    @Autowired
    lateinit var sqlArticleReadRepository: SqlArticleReadRepository

    @Test
    fun `should find article by id`() {
        val author = given.user.exists()
        val tag = tagWriteRepository.create(TagGen.build())
        val article = given.article.exist(ArticleGen.build(author, listOf(tag)))

        assertThat(sqlArticleReadRepository.findBy(article.id)).isEqualTo(article)
    }
}
