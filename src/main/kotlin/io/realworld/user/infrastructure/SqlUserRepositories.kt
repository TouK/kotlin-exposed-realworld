package io.realworld.user.infrastructure

import io.realworld.shared.infrastructure.getOrThrow
import io.realworld.shared.infrastructure.selectSingleOrNull
import io.realworld.shared.infrastructure.updateExactlyOne
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.User
import io.realworld.user.domain.UserReadRepository
import io.realworld.user.domain.UserTable
import io.realworld.user.domain.UserWriteRepository
import io.realworld.user.domain.Username
import io.realworld.user.domain.from
import io.realworld.user.domain.toUser
import org.jetbrains.exposed.sql.insert
import org.springframework.stereotype.Component

@Component
class SqlUserReadRepository : UserReadRepository {

    override fun findBy(userId: UserId) =
            UserTable.selectSingleOrNull { UserTable.id eq userId }?.toUser()

    override fun findBy(username: Username) =
            UserTable.selectSingleOrNull { UserTable.username eq username }?.toUser()

    override fun findByEmail(email: String) =
            UserTable.selectSingleOrNull { UserTable.email eq email }?.toUser()
}

@Component
class SqlUserWriteRepository : UserWriteRepository {

    override fun create(user: User) =
            UserTable.insert { it.from(user) }
                    .getOrThrow(UserTable.id)
                    .let { user.copy(id = it) }

    override fun save(user: User) {
        UserTable.updateExactlyOne({ UserTable.id eq user.id }) {
            it.from(user)
        }
    }
}
