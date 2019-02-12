package io.realworld.user.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@ComponentScan(basePackages = ["io.realworld.user"])
class UserConfiguration {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
