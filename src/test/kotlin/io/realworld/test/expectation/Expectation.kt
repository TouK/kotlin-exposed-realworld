package io.realworld.test.expectation

import org.springframework.stereotype.Component

@Component
class Expectation(
        val user: UserExpectation,
        val article: ArticleExpectation,
        val comment: CommentExpectation
)
