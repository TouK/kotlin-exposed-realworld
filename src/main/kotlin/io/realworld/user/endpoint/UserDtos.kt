package io.realworld.user.endpoint

import io.realworld.user.domain.User

data class UserResponse(
        val user: UserDto?
)

data class UserDto(
        val email: String,
        val username: String,
        val bio: String?,
        val image: String?,
        val following: Boolean,
        val token: String?
)

data class LoginRequest(
        val user: LoginDto
)

data class LoginDto(
        val email: String,
        val password: String
)

data class RegisterRequest(
        val user: RegisterUserDto
)

data class RegisterUserDto(
        val username: String,
        val email: String,
        val password: String
)

data class UpdateDto(
        val user: UpdateUserDto
)

data class UpdateUserDto(
        val email: String,
        val bio: String?,
        val image: String?
)

data class ProfileResponse(
        val profile: ProfileDto
)

data class ProfileDto(
        val username: String,
        val bio: String?,
        val image: String?,
        val following: Boolean
)

fun User.toDto(token: String? = null, following: Boolean = false) = UserDto(
        email = this.email,
        username = this.username.value,
        bio = this.bio,
        image = this.image,
        following = following,
        token = token
)

fun User.toProfileDto(following: Boolean = false) = ProfileDto(
        username = this.username.value,
        bio = this.bio,
        image = this.image,
        following = following
)
