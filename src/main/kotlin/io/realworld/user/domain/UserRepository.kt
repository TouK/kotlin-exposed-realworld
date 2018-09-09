package io.realworld.user.domain

import io.realworld.shared.refs.UserId

interface UserRepository {
    fun findById(userId: UserId) : User?
}
