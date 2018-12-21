package io.realworld.shared

import io.realworld.comment.infrastructure.TestCommentRepository
import io.realworld.user.infrastructure.TestUserRepository
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(value = [
    TestUserRepository::class, TestCommentRepository::class
])
@Configuration
class TestDataConfiguration
