package io.realworld.user.domain

import io.realworld.shared.Gen
import io.realworld.shared.refs.UserId

object UserGen {

    fun build(username: String = Gen.alphanumeric(10)) = User(
        id = UserId.New,
        username = username,
        password = Gen.alphanumeric(10)
    )
}
