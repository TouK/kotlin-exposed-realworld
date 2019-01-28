package io.realworld.user.domain

import io.realworld.security.domain.LoggedUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserFollowService(
        private val userFollowWriteRepository: UserFollowWriteRepository,
        private val userReadRepository: UserReadRepository,
        private val loggedUserService: LoggedUserService
) {

    fun follow(username: Username) = processUser(username) { user, loggedUser ->
        user.also { userFollowWriteRepository.create(user.id, loggedUser.id) }
    }

    fun unfollow(username: Username) = processUser(username) { user, loggedUser ->
        user.also { userFollowWriteRepository.delete(user.id, loggedUser.id) }
    }

    private fun processUser(username: Username, body: (User, LoggedUser) -> User): User {
        val user = userReadRepository.getBy(username)
        val loggedUser = loggedUserService.loggedUserOrThrow()
        return body(user, loggedUser)
    }
}
