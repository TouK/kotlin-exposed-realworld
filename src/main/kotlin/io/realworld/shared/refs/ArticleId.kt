package io.realworld.shared.refs

import io.realworld.shared.infrastructure.GeneratedId
import io.realworld.shared.infrastructure.IdNotPersistedDelegate

sealed class ArticleId : GeneratedId<Long>() {
    object New : ArticleId() {
        override val value: Long by IdNotPersistedDelegate<Long>()
    }

    data class Persisted(override val value: Long) : ArticleId() {
        override fun toString() = "ArticleId(value=$value)"
    }
}
