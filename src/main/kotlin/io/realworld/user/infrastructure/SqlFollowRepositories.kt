package io.realworld.user.infrastructure

import io.realworld.shared.refs.UserId
import io.realworld.user.domain.UserFollowReadRepository
import io.realworld.user.domain.UserFollowWriteRepository
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

object FollowTable : Table("follows") {
    val userId = userId("user_id") references UserTable.id
    val followerId = userId("follower_id") references UserTable.id
}

@Component
@Transactional(readOnly = true)
class SqlUserFollowReadRepository : UserFollowReadRepository {

    override fun findBy(userId: UserId): List<UserId> =
            FollowTable.select { FollowTable.userId eq userId }
                    .map { it[FollowTable.followerId] }
}

@Component
class SqlUserFollowWriteRepository : UserFollowWriteRepository {

    override fun create(userId: UserId, followerId: UserId) {
        FollowTable.insert {
            it[FollowTable.userId] = userId
            it[FollowTable.followerId] = followerId
        }
    }

    override fun delete(userId: UserId, followerId: UserId) {
        FollowTable.deleteWhere {
            (FollowTable.userId eq userId) and (FollowTable.followerId eq followerId)
        }
    }
}
