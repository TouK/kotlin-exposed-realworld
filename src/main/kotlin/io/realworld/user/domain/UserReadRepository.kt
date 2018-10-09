package io.realworld.user.domain

import io.realworld.shared.refs.UserId

interface UserReadRepository {
    fun findById(userId: UserId): User?
    fun findByUsername(username: String): User?
}
