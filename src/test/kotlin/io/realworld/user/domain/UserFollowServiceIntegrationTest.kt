package io.realworld.user.domain

import io.realworld.shared.BaseDatabaseTest
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.test.expectation.Expectation
import io.realworld.test.expectation.ExpectationConfiguration
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
import io.realworld.user.infrastructure.UserConfiguration
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
            UserConfiguration::class,
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
internal class UserFollowServiceIntegrationTest @Autowired constructor(
    val userFollowService: UserFollowService, val given: Precondition, val then: Expectation
) : BaseDatabaseTest() {

    @BeforeEach
    internal fun setUp() {
        given.user.loggedUser()
    }

    @Test
    fun `should follow and unfollow user`() {
        val user = given.user.exists()
        then.user.isNotFollowed(user.username)

        userFollowService.follow(user.username)

        then.user.isFollowed(user.username)

        userFollowService.unfollow(user.username)

        then.user.isNotFollowed(user.username)
    }
}
