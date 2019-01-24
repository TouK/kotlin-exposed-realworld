package io.realworld.precondition

import org.springframework.stereotype.Component

@Component
class Precondition(
        val user: UserPrecondition,
        val tag: TagPrecondition,
        val article: ArticlePrecondition,
        val comment: CommentPrecondition
)
