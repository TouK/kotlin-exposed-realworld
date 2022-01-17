package io.realworld.user.endpoint

import io.realworld.security.domain.LoggedUserService
import io.realworld.user.domain.UserUpdateService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(UserEndpoint.PATH)
class UserEndpoint(
        private val loggedUserService: LoggedUserService,
        private val userUpdateService: UserUpdateService
) {

    companion object {
        const val PATH = "/user"
    }

    @GetMapping
    fun current() = UserResponse(loggedUserService.loggedUser()?.toDto())

    @PutMapping
    fun update(@RequestBody user: UpdateDto) = UserResponse(
            userUpdateService.update(loggedUserService.loggedUserOrThrow(), user).toDto()
    )
}
