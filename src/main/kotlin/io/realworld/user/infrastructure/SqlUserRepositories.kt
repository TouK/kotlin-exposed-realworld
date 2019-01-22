package io.realworld.user.infrastructure

import io.realworld.shared.infrastructure.longWrapper
import io.realworld.shared.infrastructure.selectSingleOrNull
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.User
import io.realworld.user.domain.UserReadRepository
import io.realworld.user.domain.UserWriteRepository
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

object UserTable : Table("users") {
    val id = userId("id").primaryKey().autoIncrement()
    val username = text("username")
    val email = text("email")
    val password = text("password")
}

@Component
@Transactional(readOnly = true)
class SqlUserReadRepository : UserReadRepository {

    override fun findBy(userId: UserId) =
            UserTable.selectSingleOrNull { UserTable.id eq userId }?.toUser()

    override fun findByUsername(username: String) =
            UserTable.selectSingleOrNull { UserTable.username eq username }?.toUser()

    override fun findByEmail(email: String) =
            UserTable.selectSingleOrNull { UserTable.email eq email }?.toUser()
}

@Component
class SqlUserWriteRepository : UserWriteRepository {
    override fun create(user: User) =
            UserTable.insert { it.from(user) }[UserTable.id]!!
                .let { user.copy(id = it) }

    override fun save(user: User) = user.apply {
        UserTable.update({ UserTable.id eq user.id }) {
            it.from(user)
        }
    }
}

fun ResultRow.toUser(userIdColumn: Column<UserId> = UserTable.id) = User(
        id = this[userIdColumn],
        username = this[UserTable.username],
        email = this[UserTable.email],
        password = this[UserTable.password]
)

fun UpdateBuilder<Any>.from(user: User) {
    this[UserTable.username] = user.username
    this[UserTable.email] = user.email
    this[UserTable.password] = user.password
}

fun Table.userId(name: String) = longWrapper<UserId>(name, UserId::Persisted, UserId::value)
