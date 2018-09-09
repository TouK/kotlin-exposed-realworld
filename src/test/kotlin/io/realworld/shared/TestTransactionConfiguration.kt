package io.realworld.shared

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.jetbrains.exposed.sql.Database
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import javax.sql.DataSource

class TestTransactionConfiguration(private val dataSource: DataSource) {
    private val delegate = SpringTransactionManager(dataSource)

    @Bean
    @Primary
    fun testTransactionManager(): PlatformTransactionManager = object : PlatformTransactionManager by delegate {
        override fun getTransaction(definition: TransactionDefinition?): TransactionStatus {
            Database.connect(dataSource) { delegate }
            return delegate.getTransaction(definition)
        }
    }
}
