package io.realworld.test.expectation

import org.springframework.stereotype.Component

@Component
class Expectation(
        val article: ArticleExpectation
)
