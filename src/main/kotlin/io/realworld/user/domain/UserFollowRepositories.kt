package io.realworld.user.domain

import io.realworld.shared.refs.UserId

interface UserFollowReadRepository {
    fun findBy(userId: UserId): List<UserId>
}

interface UserFollowWriteRepository {
    fun create(userId: UserId, followerId: UserId)
    fun delete(userId: UserId, followerId: UserId)
}
