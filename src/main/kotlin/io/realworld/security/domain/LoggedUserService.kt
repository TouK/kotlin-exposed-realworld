package io.realworld.security.domain

import io.realworld.shared.domain.ApplicationException
import io.realworld.user.domain.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class LoggedUserService {

    fun loggedUser(): User? {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication is UsernamePasswordAuthenticationToken) {
                authentication.principal as User
        } else null
    }

    fun loggedUserOrThrow() = loggedUser() ?: throw UserNotAuthorizedException()
}

class UserNotAuthorizedException : ApplicationException("User not authorized")
