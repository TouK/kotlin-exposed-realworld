package io.realworld.user.infrastructure

import io.realworld.shared.BaseDatabaseTest
import io.realworld.shared.TestTransactionConfiguration
import io.realworld.test.precondition.Precondition
import io.realworld.test.precondition.PreconditionConfiguration
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
            SqlUserReadRepository::class,
            PreconditionConfiguration::class,
            TestTransactionConfiguration::class
        ]
)
@ImportAutoConfiguration(
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class
)

@Transactional
internal class SqlUserReadRepositoryTest @Autowired constructor(
    val userReadRepository: SqlUserReadRepository, val given: Precondition
) : BaseDatabaseTest() {

    @Test
    fun `should find user by id`() {
        val user = given.user.exists()

        assertThat(userReadRepository.findBy(user.id)).isEqualTo(user)
    }
}
