package io.realworld.user.endpoint

import io.realworld.security.domain.JwtService
import io.realworld.user.domain.UserAuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(UserEndpoint.PATH)
class UserEndpoint(private val userAuthenticationService: UserAuthenticationService,
                   private val jwtService: JwtService) {

    companion object {
        const val PATH = "/users"
        const val LOGIN_PATH = "/login"
    }

    @PostMapping(LOGIN_PATH)
    fun login(@Valid @RequestBody loginDto: LoginDto): String {
        return userAuthenticationService.authenticate(loginDto.username, loginDto.password).let(jwtService::toToken)
    }
}
