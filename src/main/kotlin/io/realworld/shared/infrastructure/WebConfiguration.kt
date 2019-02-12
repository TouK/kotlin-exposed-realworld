package io.realworld.shared.infrastructure

import io.realworld.article.domain.DuplicateArticleException
import io.realworld.article.domain.Slug
import io.realworld.shared.refs.CommentId
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest

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


@ControllerAdvice
class CoreExceptionHandler {

    @ExceptionHandler(
            MethodArgumentNotValidException::class,
            MethodArgumentTypeMismatchException::class,
            HttpMessageNotReadableException::class,
            DuplicateArticleException::class
    )
    fun handleBadRequestException(
            exception: Exception,
            request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return defaultErrorResponseEntity(HttpStatus.BAD_REQUEST, exception)
    }

    private fun defaultErrorResponseEntity(status: HttpStatus, exception: Exception) =
            ResponseEntity(
                    ErrorResponse(errors = Errors(listOf(exception.localizedMessage))), status
            )
}

data class Errors(
        val body: List<String>
)

data class ErrorResponse(
        val errors: Errors
)
