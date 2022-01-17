package io.realworld.shared.infrastructure

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.sql.SQLException
import javax.sql.DataSource

@Configuration
@Import(DatabaseExceptionTranslator::class)
@EnableTransactionManagement
class DatabaseConfiguration(
        private val dataSource: DataSource
) {
    @Bean
    fun transactionManager() = SpringTransactionManager(dataSource)
}

@Component
class DatabaseExceptionTranslator(dataSource: DataSource) {

    private val exceptionTranslator = SQLErrorCodeSQLExceptionTranslator(dataSource)

    fun <T> translateExceptions(body: () -> T): T {
        try {
            return body.invoke()
        } catch (e: SQLException) {
            throw exceptionTranslator.translate("Exposed", null, e) ?: e
        }
    }
}
