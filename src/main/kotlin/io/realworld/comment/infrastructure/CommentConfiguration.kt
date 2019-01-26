package io.realworld.comment.infrastructure

import io.realworld.security.infrastructure.SecurityConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan("io.realworld.comment")
class CommentConfiguration
