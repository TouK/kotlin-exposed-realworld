package io.realworld.precondition

import io.realworld.user.domain.User
import io.realworld.user.domain.UserGen
import io.realworld.user.domain.UserWriteRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserPrecondition(
        private val userWriteRepository: UserWriteRepository
) {
    fun exists(user: User = UserGen.build()) = userWriteRepository.create(user)

    fun loggedUser(user: User = UserGen.build()) = exists(user).also {
        SecurityContextHolder.getContext()
                .authentication = UsernamePasswordAuthenticationToken(it, null, emptyList())
    }
}
