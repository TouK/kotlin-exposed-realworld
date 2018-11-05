package io.realworld.user.endpoint

import io.realworld.security.domain.JwtService
import io.realworld.user.domain.UserAuthenticationService
import io.realworld.user.domain.UserRegisterService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

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
    fun login(@Valid @RequestBody loginRequest: LoginRequest) = UserResponse(
            loginRequest.user.run {
                val user = userAuthenticationService.authenticate(email, password)
                user.toDto(jwtService.toToken(user))
            })

    @PostMapping
    fun register(@Valid @RequestBody registerRequest: RegisterRequest) = UserResponse(
            registerRequest.user.run {
                userRegisterService.register(username, email, password).toDto()
            })
}
