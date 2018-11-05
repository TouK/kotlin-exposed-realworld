package io.realworld.security.infrastructure

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.realworld.security.domain.JwtService
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtsService(@param:Value("\${realworld.jwt.secret}") private val secret: String,
                  @param:Value("\${realworld.jwt.sessionTime}") private val sessionTime: Int) : JwtService {

    override fun toToken(user: User) = Jwts.builder()
            .setSubject(user.id.value.toString())
            .setExpiration(expireTimeFromNow())
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()

    override fun getSubFromToken(token: String): UserId? {
        val claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
        return UserId.Persisted(claimsJws.body.subject.toLong())
    }

    private fun expireTimeFromNow() =
            Date(System.currentTimeMillis() + sessionTime * 1000)
}
