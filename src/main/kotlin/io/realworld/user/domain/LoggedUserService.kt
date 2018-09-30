package io.realworld.user.domain

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class LoggedUserService {

    fun loggedUser(): User {
        return SecurityContextHolder.getContext().authentication.principal as User
    }
}
