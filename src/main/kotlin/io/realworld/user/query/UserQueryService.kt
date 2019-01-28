package io.realworld.user.query

import io.realworld.shared.refs.UserId
import io.realworld.user.domain.UserReadRepository
import io.realworld.user.domain.Username
import org.springframework.stereotype.Service

@Service
class UserQueryService(
        private val userReadRepository: UserReadRepository
) {
    fun getBy(userId: UserId) = userReadRepository.getBy(userId)

    fun getBy(username: Username) = userReadRepository.getBy(username)
}
