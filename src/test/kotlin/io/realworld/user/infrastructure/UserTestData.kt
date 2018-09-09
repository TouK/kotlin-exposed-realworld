package io.realworld.user.infrastructure

import io.realworld.user.domain.User
import io.realworld.user.domain.UserGen
import io.realworld.user.infrastructure.db.UserTable
import io.realworld.user.infrastructure.db.from
import org.jetbrains.exposed.sql.insert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UserTestData {

    fun insert(user: User = UserGen.build()) =
            UserTable.insert { it.from(user) }[UserTable.id]!!
                     .let { user.copy(id = it) }
}
