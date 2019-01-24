package io.realworld.article.domain

import io.realworld.article.infrastructure.ArticleConfiguration
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
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
internal class TagServiceIntegrationTest {

    @Autowired
    lateinit var given: Precondition

    @Autowired
    lateinit var tagReadRepository: TagReadRepository

    @Autowired
    lateinit var tagService: TagService

    @Test
    fun `should store or find tags`() {
        val tagAlpha = TagGen.build()
        val tagBravo = TagGen.build()
        val tagCharlie = TagGen.build()
        val tagDelta = TagGen.build()
        val tagEcho = TagGen.build()

        val tagNames = arrayOf(tagAlpha, tagBravo, tagCharlie, tagDelta, tagEcho).map(Tag::name)

        arrayOf(tagAlpha, tagBravo, tagCharlie).forEach { given.tag.exists(it) }

        val tags = tagService.storeOrLoad(tagNames)

        assertThat(tags)
                .extracting<String> { it.name }
                .containsExactlyInAnyOrderElementsOf(tagNames)

        assertThat(tagReadRepository.findByNames(tagNames))
                .extracting<String> { it.name }
                .containsExactlyInAnyOrderElementsOf(tagNames)
    }
}
