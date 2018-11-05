package io.realworld.user.domain

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAuthenticationService(private val userReadRepository: UserReadRepository,
                                private val passwordEncoder: PasswordEncoder) {

    fun authenticate(email: String, password: String): User {
        return userReadRepository.findByEmail(email)?.let { user ->
            if (passwordEncoder.matches(password, user.password)) user else null
        } ?: throw BadCredentialsException("Bad credentials")
    }
}
