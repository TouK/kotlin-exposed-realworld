package io.realworld.user.domain

import io.realworld.user.endpoint.UpdateDto
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserUpdateService(
        private val userReadRepository: UserReadRepository,
        private val userWriteRepository: UserWriteRepository
) {
    
    fun update(user: User, updateDto: UpdateDto): User {
        return userReadRepository.getBy(user.id)
                .copy(email = updateDto.user.email)
                .also(userWriteRepository::save)
    }
}
