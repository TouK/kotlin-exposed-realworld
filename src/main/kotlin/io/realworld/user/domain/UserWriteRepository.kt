package io.realworld.user.domain

interface UserWriteRepository {
    fun create(user: User): User

    fun save(user: User): User
}
