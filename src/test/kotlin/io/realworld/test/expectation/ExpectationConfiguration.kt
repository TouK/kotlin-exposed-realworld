package io.realworld.test.expectation

import io.realworld.article.infrastructure.SqlArticleReadRepository
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan("io.realworld.test.expectation")
@Import(value = [SqlArticleReadRepository::class])
class ExpectationConfiguration
