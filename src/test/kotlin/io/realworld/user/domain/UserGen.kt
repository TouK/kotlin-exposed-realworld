package io.realworld.user.domain

import io.realworld.shared.Gen
import io.realworld.shared.refs.UserId

object UserGen {

    fun build(username: String = Gen.alphanumeric(10), email: String = Gen.email()) = User(
        id = UserId.New,
        username = username,
        email = email,
        password = Gen.alphanumeric(10)
    )
}
