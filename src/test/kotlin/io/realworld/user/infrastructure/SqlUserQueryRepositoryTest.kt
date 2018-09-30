package io.realworld.user.infrastructure

import io.realworld.shared.TestDataConfiguration
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
            SqlUserQueryRepository::class,
            TestDataConfiguration::class,
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)

@Transactional
internal class SqlUserQueryRepositoryTest {

    @Autowired
    lateinit var sqlUserRepository: SqlUserQueryRepository

    @Autowired
    lateinit var testUserRepository: TestUserRepository

    @Test
    fun `should find user by id`() {
        val user = testUserRepository.insert()

        assertThat(sqlUserRepository.findById(user.id)).isEqualTo(user)
    }
}
