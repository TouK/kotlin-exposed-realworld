package io.realworld.article.domain

import io.realworld.shared.infrastructure.GeneratedId
import io.realworld.shared.infrastructure.IdNotPersistedDelegate

sealed class TagId : GeneratedId<Long>() {
    object New : TagId() {
        override val value: Long by IdNotPersistedDelegate<Long>()
    }

    data class Persisted(override val value: Long) : TagId() {
        override fun toString() = "TagId(value=$value)"
    }
}

data class Tag(
        val id: TagId,
        val name: String
)
