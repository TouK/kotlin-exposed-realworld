package io.realworld.article.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.realworld.article.domain.ArticleService
import io.realworld.article.domain.DuplicateArticleException
import io.realworld.article.domain.Slug
import io.realworld.article.infrastructure.ArticleConfiguration
import io.realworld.security.infrastructure.SecurityConfiguration
import io.realworld.shared.infrastructure.DatabaseConfiguration
import io.realworld.shared.infrastructure.WebConfiguration
import io.realworld.test.json
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@WebMvcTest(
        ArticleEndpoint::class, ArticleConfiguration::class, PreconditionConfiguration::class,
        WebConfiguration::class, SecurityConfiguration::class, DatabaseConfiguration::class
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
@Transactional
class ArticleEndpointTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var given: Precondition

    @MockBean
    lateinit var articleService: ArticleService

    @BeforeEach
    internal fun setUp() {
        given.user.loggedUser()
    }

    @Test
    fun `should return error on duplicate article slug`() {
        whenever(articleService.create(any())).thenThrow(DuplicateArticleException(Slug("someSlug")))

        mvc.perform(
                post(ArticleEndpoint.PATH).jsonBody(CreateArticleRequest(CreateArticleDtoGen.build())))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.errors.body[0]", equalTo("Duplicate article for slug someSlug")))
    }

    private fun MockHttpServletRequestBuilder.jsonBody(body: Any) = this.json(objectMapper.writeValueAsString(body))
}
