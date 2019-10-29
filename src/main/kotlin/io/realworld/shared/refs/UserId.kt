package io.realworld.shared.refs

import io.realworld.shared.infrastructure.RefId
import io.realworld.shared.infrastructure.IdNotPersistedDelegate
import pl.touk.exposed.Converter

sealed class UserId : RefId<Long>() {
    object New : UserId() {
        override val value: Long by IdNotPersistedDelegate<Long>()
    }

    data class Persisted(override val value: Long) : UserId() {
        override fun toString() = "UserId(value=$value)"
    }
}

class UserIdConverter : Converter<UserId, Long> {
    override fun convertToDatabaseColumn(attribute: UserId): Long {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: Long): UserId {
        return UserId.Persisted(dbData)
    }
}
