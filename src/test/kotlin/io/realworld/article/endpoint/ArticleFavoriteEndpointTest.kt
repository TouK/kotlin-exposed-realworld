package io.realworld.article.endpoint

import com.nhaarman.mockito_kotlin.whenever
import io.realworld.article.domain.ArticleFavoriteService
import io.realworld.article.domain.ArticleGen
import io.realworld.article.infrastructure.ArticleConfiguration
import io.realworld.security.infrastructure.SecurityConfiguration
import io.realworld.shared.infrastructure.DatabaseConfiguration
import io.realworld.shared.infrastructure.WebConfiguration
import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.UserId
import io.realworld.test.json
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
import io.realworld.user.domain.UserGen
import io.realworld.user.query.UserQueryService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@WebMvcTest(
        ArticleFavoriteEndpoint::class, ArticleConfiguration::class, PreconditionConfiguration::class,
        WebConfiguration::class, SecurityConfiguration::class, DatabaseConfiguration::class
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
@Transactional
class ArticleFavoriteEndpointTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var given: Precondition

    @MockBean
    lateinit var articleFavoriteService: ArticleFavoriteService

    @MockBean
    lateinit var userQueryService: UserQueryService

    @BeforeEach
    internal fun setUp() {
        given.user.loggedUser()
    }

    @Test
    fun `should favorite article`() {
        val userId = UserId.Persisted(1)
        val user = UserGen.build().copy(id = userId)
        val article = ArticleGen.build(user).copy(id = ArticleId.Persisted(2))

        whenever(userQueryService.getBy(userId)).thenReturn(user)
        whenever(articleFavoriteService.favorite(article.slug)).thenReturn(article)

        mvc.perform(
                post("${ArticleEndpoint.PATH}/${article.slug.value}/${ArticleFavoriteEndpoint.FAVORITE_PATH}")
                    .json("")
        ).andExpect(status().isOk)
    }

}
