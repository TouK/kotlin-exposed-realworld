package io.realworld.shared.refs

import io.realworld.shared.infrastructure.RefId
import io.realworld.shared.infrastructure.IdNotPersistedDelegate

sealed class UserId : RefId<Long>() {
    object New : UserId() {
        override val value: Long by IdNotPersistedDelegate<Long>()
    }

    data class Persisted(override val value: Long) : UserId() {
        override fun toString() = "UserId(value=$value)"
    }
}
