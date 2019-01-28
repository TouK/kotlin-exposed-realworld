package io.realworld.user.endpoint

import io.realworld.security.domain.LoggedUserService
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.LoggedUser
import io.realworld.user.domain.User
import io.realworld.user.domain.UserFollowReadRepository
import org.springframework.stereotype.Component

@Component
class UserConverter(
        private val userFollowReadRepository: UserFollowReadRepository,
        private val loggedUserService: LoggedUserService
) {
    fun toProfileDto(user: User, loggedUser: LoggedUser? = loggedUserService.loggedUserOrThrow()) =
            user.toProfileDto(loggedUser.follows(user.id))

    private fun LoggedUser?.follows(userId: UserId) =
            this?.let { userFollowReadRepository.findBy(userId).contains(id) } ?: false
}
