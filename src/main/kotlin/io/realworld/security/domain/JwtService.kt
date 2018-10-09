package io.realworld.security.domain

import io.realworld.shared.refs.UserId
import io.realworld.user.domain.User
import org.springframework.stereotype.Service

@Service
interface JwtService {
    fun toToken(user: User): String
    fun getSubFromToken(token: String): UserId?
}
