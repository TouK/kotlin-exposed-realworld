package io.realworld.article.query

import io.realworld.article.domain.ArticleGen
import io.realworld.article.domain.ArticleNotFoundException
import io.realworld.article.domain.Slug
import io.realworld.article.domain.Tag
import io.realworld.article.infrastructure.ArticleConfiguration
import io.realworld.shared.Gen
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
internal class ArticleQueryServiceTest
@Autowired constructor(val articleQueryService: ArticleQueryService, val given: Precondition) {

    lateinit var tags: List<Tag>

    @BeforeEach
    internal fun setUp() {
        given.article.empty()
        val tagAlpha = given.tag.exists()
        val tagBravo = given.tag.exists()
        tags = listOf(tagAlpha, tagBravo)
    }

    @Test
    fun `should return article with tags`() {
        val author = given.user.exists()
        val article = given.article.exist(ArticleGen.build(author = author, tags = tags))

        val savedArticle = articleQueryService.getBy(article.slug)

        assertThat(savedArticle)
            .isEqualToIgnoringGivenFields(article, "createdAt", "updatedAt")
    }

    @Test
    fun `should return article without tags`() {
        val author = given.user.exists()
        val article = given.article.exist(ArticleGen.build(author = author, tags = emptyList()))

        val savedArticle = articleQueryService.getBy(article.slug)

        assertThat(savedArticle)
            .isEqualToIgnoringGivenFields(article, "createdAt", "updatedAt")
    }

    @Test
    fun `should return multiple articles`() {
        val author = given.user.exists()
        val articleAlpha = given.article.exist(ArticleGen.build(author = author, tags = tags))
        val articleBravo = given.article.exist(ArticleGen.build(author = author, tags = emptyList()))

        val articles = articleQueryService.findAll()
        val (persistedArticleAlpha, persistedArticleBravo) = articles

        assertThat(articles).hasSize(2)
        assertThat(persistedArticleAlpha).isEqualToIgnoringGivenFields(articleAlpha, "tags", "createdAt", "updatedAt")
        assertThat(persistedArticleAlpha.tags).containsAnyElementsOf(articleAlpha.tags)
        assertThat(persistedArticleBravo).isEqualToIgnoringGivenFields(articleBravo, "tags", "createdAt", "updatedAt")
        assertThat(persistedArticleBravo.tags).isEmpty()
    }

    @Test
    fun `should not return unknown article`() {
        assertThatThrownBy {
            articleQueryService.getBy(Slug(Gen.alphanumeric(3)))
        }.isInstanceOf(ArticleNotFoundException::class.java)
    }
}
