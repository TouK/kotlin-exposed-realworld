package io.realworld.shared.refs

import io.realworld.shared.infrastructure.GeneratedId
import io.realworld.shared.infrastructure.IdNotPersistedDelegate

sealed class CommentId : GeneratedId<Long>() {
    object New : CommentId() {
        override val value: Long by IdNotPersistedDelegate<Long>()
    }

    data class Persisted(override val value: Long) : CommentId() {
        override fun toString() = "ArticleId(value=$value)"
    }
}
