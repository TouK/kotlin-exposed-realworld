package io.realworld.shared.refs

import io.realworld.shared.infrastructure.GeneratedId
import io.realworld.shared.infrastructure.IdNotPersistedDelegate

sealed class UserId : GeneratedId<Long>() {
    object New : UserId() {
        override val value: Long by IdNotPersistedDelegate<Long>()
    }

    data class Persisted(override val value: Long) : UserId() {
        override fun toString() = "UserId(value=$value)"
    }
}
