package io.realworld.article.infrastructure

import io.realworld.shared.TestTransactionConfiguration
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
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
class SqlTagReadRepositoryTest {

    @Autowired
    lateinit var tagReadRepository: SqlTagReadRepository

    @Autowired
    lateinit var given: Precondition

    @BeforeEach
    internal fun setUp() {
        given.tag.empty()
    }

    @Test
    fun `should find tags by name`() {
        val tagAlpha = given.tag.exists()
        given.tag.exists()

        assertThat(tagReadRepository.findAllByNames(listOf(tagAlpha.name))).containsExactly(tagAlpha)
    }

    @Test
    fun `should find all tags`() {
        val tagAlpha = given.tag.exists()
        val tagBravo = given.tag.exists()

        assertThat(tagReadRepository.findAll()).containsExactlyInAnyOrder(tagAlpha, tagBravo)
    }
}
