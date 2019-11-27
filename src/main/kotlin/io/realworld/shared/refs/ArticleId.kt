package io.realworld.shared.refs

import io.realworld.shared.infrastructure.RefId
import io.realworld.shared.infrastructure.IdNotPersistedDelegate
import pl.touk.krush.Converter

sealed class ArticleId : RefId<Long>() {
    object New : ArticleId() {
        override val value: Long by IdNotPersistedDelegate<Long>()
    }

    data class Persisted(override val value: Long) : ArticleId() {
        override fun toString() = "ArticleId(value=$value)"
    }
}

class ArticleIdConverter : Converter<ArticleId, Long> {
    override fun convertToDatabaseColumn(attribute: ArticleId): Long {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: Long): ArticleId {
        return ArticleId.Persisted(dbData)
    }
}
