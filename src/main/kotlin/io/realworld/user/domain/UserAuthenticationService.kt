package io.realworld.user.domain

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserAuthenticationService(private val userReadRepository: UserReadRepository,
                                private val passwordEncoder: PasswordEncoder) {

    fun authenticate(username: String, password: String): User {
        return userReadRepository.findByUsername(username)?.let { user ->
            if (passwordEncoder.matches(password, user.password)) user else null
        } ?: throw BadCredentialsException("Bad credentials")
    }
}
