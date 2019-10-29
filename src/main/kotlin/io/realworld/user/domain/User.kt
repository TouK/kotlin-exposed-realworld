package io.realworld.user.domain

import io.realworld.shared.refs.UserId
import io.realworld.shared.refs.UserIdConverter
import pl.touk.exposed.Convert
import pl.touk.exposed.Converter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(

        @Id @GeneratedValue
        @Convert(value = UserIdConverter::class)
        val id: UserId = UserId.New,

        @Convert(value = UsernameConverter::class)
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

class UsernameConverter : Converter<Username, String> {
    override fun convertToDatabaseColumn(attribute: Username): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): Username {
        return Username(dbData)
    }

}
