package io.realworld.user.query

import io.realworld.shared.refs.UserId
import io.realworld.user.domain.UserReadRepository
import io.realworld.user.domain.Username
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserQueryService(
        private val userReadRepository: UserReadRepository
) {
    fun findBy(userId: UserId) = userReadRepository.findBy(userId)

    fun getBy(userId: UserId) = userReadRepository.getBy(userId)

    fun getBy(username: Username) = userReadRepository.getBy(username)
}
