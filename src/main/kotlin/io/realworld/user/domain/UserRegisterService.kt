package io.realworld.user.domain

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserRegisterService(
        private val passwordEncoder: PasswordEncoder,
        private val userWriteRepository: UserWriteRepository
) {

    fun register(username: String, email: String, password: String): User {
        val user = User(
                username = username,
                password = passwordEncoder.encode(password),
                email = email
        )
        return userWriteRepository.save(user)
    }
}
