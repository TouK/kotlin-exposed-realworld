package io.realworld.shared

import io.realworld.user.infrastructure.UserTestData
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(UserTestData::class)
@Configuration
class TestDataConfiguration
