package io.realworld.user.infrastructure

import io.realworld.shared.infrastructure.longWrapper
import io.realworld.shared.infrastructure.selectSingleOrNull
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.User
import io.realworld.user.domain.UserReadRepository
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.springframework.stereotype.Component

object UserTable : Table("users") {
    val id = userId("id").primaryKey().autoIncrement()
    val username = text("username")
    val password = text("password")
}

@Component
class SqlUserReadRepository : UserReadRepository {
    override fun findById(userId: UserId) =
            UserTable.selectSingleOrNull { UserTable.id eq userId }?.toUser()

    override fun findByUsername(username: String) =
            UserTable.selectSingleOrNull { UserTable.username eq username }?.toUser()
}

fun ResultRow.toUser(userIdColumn: Column<UserId> = UserTable.id) = User(
        id = this[userIdColumn],
        username = this[UserTable.username],
        password = this[UserTable.password]
)

fun UpdateBuilder<Any>.from(user: User) {
    this[UserTable.username] = user.username
    this[UserTable.password] = user.password
}

fun Table.userId(name: String) = longWrapper<UserId>(name, UserId::Persisted, UserId::value)
