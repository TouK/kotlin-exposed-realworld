package io.realworld.shared.infrastructure

import io.realworld.article.domain.Slug
import io.realworld.shared.refs.CommentId
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(SlugConverter())
        registry.addConverter(CommentIdConverter())
    }
}

class SlugConverter : Converter<String, Slug> {
    override fun convert(source: String) = Slug(source)
}

class CommentIdConverter : Converter<String, CommentId> {
    override fun convert(source: String) = CommentId.Persisted(source.toLong())
}
