package io.realworld.user.domain

import io.realworld.shared.refs.UserId

interface UserReadRepository {
    fun findBy(userId: UserId): User?
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?

    fun getBy(userId: UserId) = findBy(userId) ?: throw UserNotFoundException(userId)
}
