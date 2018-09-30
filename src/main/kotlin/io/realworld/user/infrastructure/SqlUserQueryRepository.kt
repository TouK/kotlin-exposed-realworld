package io.realworld.user.infrastructure

import io.realworld.shared.infrastructure.longWrapper
import io.realworld.shared.infrastructure.selectSingleOrNull
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.User
import io.realworld.user.domain.UserQueryRepository
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.springframework.stereotype.Component

object UserTable : Table("users") {
    val id = userId("id").primaryKey().autoIncrement()
    val username = text("username")
}

@Component
class SqlUserQueryRepository : UserQueryRepository {
    override fun findById(userId: UserId) =
            UserTable.selectSingleOrNull { UserTable.id eq userId }?.toUser()
}

fun ResultRow.toUser(userIdColumn: Column<UserId> = UserTable.id) = User(
        id = this[userIdColumn],
        username = this[UserTable.username]
)

fun UpdateBuilder<Any>.from(user: User) {
    this[UserTable.username] = user.username
}

fun Table.userId(name: String) = longWrapper<UserId>(name, UserId::Persisted, UserId::value)
