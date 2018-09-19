package io.realworld.user.endpoint

import io.realworld.user.domain.User

data class UserDto(
        val username: String,
        val bio: String,
        val image: String,
        val following: Boolean
)

fun User.toDto() = UserDto(
        username = this.username,
        bio = "",
        image = "",
        following = false
)
