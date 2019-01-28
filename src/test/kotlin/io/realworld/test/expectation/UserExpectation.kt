package io.realworld.test.expectation

import io.realworld.user.domain.UserFollowReadRepository
import io.realworld.user.domain.Username
import io.realworld.user.endpoint.UserConverter
import io.realworld.user.query.UserQueryService
import org.assertj.core.api.Assertions.assertThat
import org.springframework.stereotype.Component

@Component
class UserExpectation(
        private val userQueryService: UserQueryService,
        private val userFollowReadRepository: UserFollowReadRepository,
        private val userConverter: UserConverter
) {

    fun isFollowed(username: Username) {
        assertThat(getBy(username).following).isTrue()
    }

    fun isNotFollowed(username: Username) {
        assertThat(getBy(username).following).isFalse()
    }

    private fun getBy(username: Username) = userQueryService.getBy(username).let { userConverter.toProfileDto(it) }
}
