package io.realworld.user.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import io.realworld.security.infrastructure.SecurityConfiguration
import io.realworld.shared.Gen
import io.realworld.shared.refs.UserId
import io.realworld.test.json
import io.realworld.user.domain.UserGen
import io.realworld.user.domain.UserReadRepository
import io.realworld.user.infrastructure.UserConfiguration
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(UsersEndpoint::class, UserConfiguration::class, SecurityConfiguration::class)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)
class UsersEndpointTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var userReadRepository: UserReadRepository

    @MockBean
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun `should login on correct credentials`() {
        val user = UserGen.build().copy(id = UserId.Persisted(Gen.id()))
        whenever(userReadRepository.findByEmail(user.email)).thenReturn(user)
        whenever(passwordEncoder.matches(eq(user.password), any())).thenReturn(true)

        mvc.perform(
                post("${UsersEndpoint.PATH}/${UsersEndpoint.LOGIN_PATH}").jsonBody(LoginRequest(user = LoginDto(user.email, user.password))))
                .andExpect(status().isOk)
    }

    private fun MockHttpServletRequestBuilder.jsonBody(body: Any) = this.json(objectMapper.writeValueAsString(body))
}
