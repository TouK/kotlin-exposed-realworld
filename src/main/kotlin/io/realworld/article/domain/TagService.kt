package io.realworld.article.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class TagService(
        private val tagReadRepository: TagReadRepository,
        private val tagWriteRepository: TagWriteRepository
) {

    fun storeOrLoad(tagNames: List<String>): List<Tag> {
        val stored = tagReadRepository.findByNames(tagNames)
        val remainingTags = tagNames.toSet() - stored.map { it.name }
        val storedRemaining = remainingTags.map { tagWriteRepository.save(Tag(name = it)) }
        return stored + storedRemaining
    }
}
