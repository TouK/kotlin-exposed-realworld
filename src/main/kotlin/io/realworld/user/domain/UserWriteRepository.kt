package io.realworld.user.domain

interface UserWriteRepository {
    fun save(user: User): User
}
