package io.realworld.user.domain

import io.realworld.shared.refs.UserId

data class User(
        val id: UserId = UserId.New,
        val username: String,
        val email: String,
        val password: String
)

typealias Author = User
typealias LoggedUser = User
