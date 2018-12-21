package io.realworld.article.domain

interface TagReadRepository {
    fun findByNames(names: List<String>): List<Tag>
}

interface TagWriteRepository {
    fun save(tag: Tag): Tag
}
