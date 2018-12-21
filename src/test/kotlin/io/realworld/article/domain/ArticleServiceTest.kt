package io.realworld.article.domain

import io.realworld.article.endpoint.CreateArticleDtoGen
import io.realworld.article.infrastructure.ArticleConfiguration
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        classes = [
            ArticleConfiguration::class,
            TestUserRepository::class,
            TestDataConfiguration::class,
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
@Transactional
internal class ArticleServiceTest {

    @Autowired
    lateinit var testUserRepository: TestUserRepository

    @Autowired
    lateinit var tagWriteRepository: TagWriteRepository

    @Autowired
    lateinit var articleService: ArticleService

    @Test
    fun `should save article`() {
        val user = testUserRepository.insert()
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user, null, emptyList())

        val tagAlpha = TagGen.build()
        val tagBravo = TagGen.build()
        val tagNames = arrayOf(tagAlpha, tagBravo).map(Tag::name)

        tagWriteRepository.save(tagAlpha)

        val createArticleDto = CreateArticleDtoGen.build(tags = tagNames)

        val articleDto = articleService.create(createArticleDto)

        assertThat(articleDto.title).isEqualTo(createArticleDto.title)
        assertThat(articleDto.description).isEqualTo(createArticleDto.description)
        assertThat(articleDto.body).isEqualTo(createArticleDto.body)
        assertThat(articleDto.tagList).isEqualTo(tagNames)
    }
}
