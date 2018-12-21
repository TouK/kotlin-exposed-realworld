package io.realworld.user.endpoint

import io.realworld.security.domain.LoggedUserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(UserEndpoint.PATH)
class UserEndpoint(private val loggedUserService: LoggedUserService) {

    companion object {
        const val PATH = "/user"
    }

    @GetMapping
    fun current() = UserResponse(loggedUserService.loggedUser()?.toDto())
}
