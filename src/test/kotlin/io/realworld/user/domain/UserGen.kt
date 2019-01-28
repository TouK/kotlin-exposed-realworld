package io.realworld.user.domain

import io.realworld.shared.Gen
import io.realworld.shared.refs.UserId

object UserGen {

    fun build(username: String = Gen.alphanumeric(10), email: String = Gen.email()) = User(
            id = UserId.New,
            username = Username(username),
            email = email,
            password = Gen.alphanumeric(10),
            bio = Gen.alphanumeric(200),
            image = Gen.alphanumeric(10)
    )
}
