package io.realworld.test.precondition

import io.realworld.article.domain.Tag
import io.realworld.article.domain.TagGen
import io.realworld.article.domain.TagWriteRepository
import org.springframework.stereotype.Component

@Component
class TagPrecondition(
        private val tagWriteRepository: TagWriteRepository
) {
    fun exists(tag: Tag = TagGen.build()) = tagWriteRepository.create(tag)
}
