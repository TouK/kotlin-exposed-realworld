package io.realworld.user.domain

import io.realworld.shared.refs.UserId

data class User(
        val id: UserId = UserId.New,
        val username: Username,
        val password: String,
        val email: String,
        val bio: String?,
        val image: String?
)

typealias Author = User
typealias LoggedUser = User

data class Username(
        val value: String
)
