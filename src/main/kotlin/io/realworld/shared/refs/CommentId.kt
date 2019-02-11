package io.realworld.shared.refs

import com.fasterxml.jackson.annotation.JsonValue
import io.realworld.shared.infrastructure.RefId
import io.realworld.shared.infrastructure.IdNotPersistedDelegate

sealed class CommentId : RefId<Long>() {
    object New : CommentId() {
        override val value: Long by IdNotPersistedDelegate<Long>()
    }

    data class Persisted(override @get:JsonValue val value: Long) : CommentId() {
        override fun toString() = "ArticleId(value=$value)"
    }
}
