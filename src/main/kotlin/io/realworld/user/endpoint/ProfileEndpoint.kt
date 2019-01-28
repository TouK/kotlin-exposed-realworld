package io.realworld.user.endpoint

import io.realworld.user.domain.UserFollowService
import io.realworld.user.domain.Username
import io.realworld.user.query.UserQueryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ProfileEndpoint.PATH)
class ProfileEndpoint(
        private val userQueryService: UserQueryService,
        private val userFollowService: UserFollowService,
        private val userConverter: UserConverter
) {

    companion object {
        const val PATH = "/profiles"
        const val USERNAME_PARAM = "username"
        const val FOLLOW_PATH = "/follow"
    }

    @GetMapping("{$USERNAME_PARAM}")
    fun get(@PathVariable(value = USERNAME_PARAM, required = true) username: Username) =
            getBy(username).let(::ProfileResponse)


    @PostMapping("{$USERNAME_PARAM}/$FOLLOW_PATH")
    fun follow(@PathVariable(value = USERNAME_PARAM, required = true) username: Username): ProfileResponse {
        userFollowService.follow(username)
        return getBy(username).let(::ProfileResponse)
    }

    @DeleteMapping("{$USERNAME_PARAM}/$FOLLOW_PATH")
    fun unfollow(@PathVariable(value = USERNAME_PARAM, required = true) username: Username): ProfileResponse {
        userFollowService.unfollow(username)
        return getBy(username).let(::ProfileResponse)
    }

    private fun getBy(username: Username) =
            userConverter.toProfileDto(userQueryService.getBy(username))
}
