package io.realworld.user.domain

import io.realworld.shared.domain.ApplicationException
import io.realworld.shared.refs.UserId

interface UserReadRepository {
    fun findBy(userId: UserId): User?
    fun findBy(username: Username): User?
    fun findByEmail(email: String): User?

    fun getBy(userId: UserId) = findBy(userId) ?: throw UserNotFoundException(userId)
    fun getBy(username: Username) = findBy(username) ?: throw UserNotFoundException(username)
}

interface UserWriteRepository {
    fun create(user: User): User
    fun save(user: User)
}

class UserNotFoundException : ApplicationException {
    constructor(userId: UserId) : super("User with id $userId not found")
    constructor(username: Username) : super("User with username ${username.value} not found")
}
