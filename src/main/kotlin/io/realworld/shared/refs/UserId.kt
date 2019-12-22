package io.realworld.shared.refs

import io.realworld.shared.infrastructure.IdNotPersistedDelegate
import io.realworld.shared.infrastructure.RefId
import javax.persistence.AttributeConverter

sealed class UserId : RefId<Long>() {
    object New : UserId() {
        override val value: Long by IdNotPersistedDelegate<Long>()
    }

    data class Persisted(override val value: Long) : UserId() {
        override fun toString() = "UserId(value=$value)"
    }
}

class UserIdConverter : AttributeConverter<UserId, Long> {
    override fun convertToDatabaseColumn(attribute: UserId): Long {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: Long): UserId {
        return UserId.Persisted(dbData)
    }
}
