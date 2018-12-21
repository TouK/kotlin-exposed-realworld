package io.realworld.shared.infrastructure

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
class JacksonConfiguration {

    @Bean
    fun jacksonObjectMapperCustomization(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer {
            it.timeZone(TimeZone.getTimeZone("UTC"))
        }
    }
}
