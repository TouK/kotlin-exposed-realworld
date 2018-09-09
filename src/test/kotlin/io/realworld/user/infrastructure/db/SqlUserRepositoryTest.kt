package io.realworld.user.infrastructure.db

import io.realworld.shared.TestDataConfiguration
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.user.infrastructure.UserTestData
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
            SqlUserRepository::class,
            TestDataConfiguration::class,
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)

@Transactional
internal class SqlUserRepositoryTest {

    @Autowired
    lateinit var sqlUserRepository: SqlUserRepository

    @Autowired
    lateinit var userTestData: UserTestData

    @Test
    fun `should find user by id`() {
        val user = userTestData.insert()

        assertThat(sqlUserRepository.findById(user.id)?.username).isEqualTo(user.username)
    }
}
