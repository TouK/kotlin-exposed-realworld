package io.realworld.user.endpoint

import io.realworld.security.domain.JwtService
import io.realworld.user.domain.UserAuthenticationService
import io.realworld.user.domain.UserRegisterService
import io.realworld.user.domain.Username
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(UsersEndpoint.PATH)
class UsersEndpoint(
        private val userAuthenticationService: UserAuthenticationService,
        private val userRegisterService: UserRegisterService,
        private val jwtService: JwtService
) {

    companion object {
        const val PATH = "/users"
        const val LOGIN_PATH = "/login"
    }

    @PostMapping(LOGIN_PATH)
    fun login(@RequestBody loginRequest: LoginRequest) = UserResponse(
            loginRequest.user.run {
                val user = userAuthenticationService.authenticate(email, password)
                user.toDto(jwtService.toToken(user))
            })

    @PostMapping
    fun register(@RequestBody registerRequest: RegisterRequest) = UserResponse(
            registerRequest.user.run {
                userRegisterService.register(Username(username), email, password).toDto()
            })
}
