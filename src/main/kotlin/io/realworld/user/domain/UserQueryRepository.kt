package io.realworld.user.domain

import io.realworld.shared.refs.UserId

interface UserQueryRepository {
    fun findById(userId: UserId) : User?
}
