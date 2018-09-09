package io.realworld.user.domain

import io.realworld.shared.refs.UserId

data class User(
        val id: UserId = UserId.New,
        val username: String
)
