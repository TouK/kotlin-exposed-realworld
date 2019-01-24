package io.realworld.article.infrastructure

import io.realworld.security.infrastructure.SecurityConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan("io.realworld.article")
@Import(SecurityConfiguration::class)
class ArticleConfiguration
