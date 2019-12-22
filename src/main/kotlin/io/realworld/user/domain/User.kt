package io.realworld.user.domain

import io.realworld.shared.refs.UserId
import io.realworld.shared.refs.UserIdConverter
import javax.persistence.AttributeConverter
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(

        @Id @GeneratedValue
        @Convert(converter = UserIdConverter::class)
        val id: UserId = UserId.New,

        @Convert(converter = UsernameConverter::class)
        val username: Username,

        val password: String,

        val email: String,

        val bio: String?,

        val image: String?
)

typealias Author = User
typealias LoggedUser = User

data class Username(
        val value: String
)

class UsernameConverter : AttributeConverter<Username, String> {
    override fun convertToDatabaseColumn(attribute: Username): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): Username {
        return Username(dbData)
    }

}
