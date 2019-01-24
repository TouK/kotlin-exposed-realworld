package io.realworld.shared.infrastructure

import io.realworld.article.domain.Slug
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(SlugConverter())
    }
}

class SlugConverter : Converter<String, Slug> {
    override fun convert(source: String) = Slug(source)
}
