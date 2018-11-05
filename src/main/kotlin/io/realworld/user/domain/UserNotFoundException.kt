package io.realworld.user.domain

import io.realworld.shared.domain.ApplicationException
import io.realworld.shared.refs.UserId

class UserNotFoundException(userId: UserId) : ApplicationException("User with id $userId not found")
