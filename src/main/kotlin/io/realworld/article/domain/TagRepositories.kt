package io.realworld.article.domain

interface TagReadRepository {
    fun findAllByNames(names: List<String>): List<Tag>
    fun findAll(): List<Tag>
}

interface TagWriteRepository {
    fun create(tag: Tag): Tag
}
