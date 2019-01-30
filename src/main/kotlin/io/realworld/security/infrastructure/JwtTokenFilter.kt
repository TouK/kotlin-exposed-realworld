package io.realworld.security.infrastructure

import io.realworld.security.domain.JwtService
import io.realworld.user.domain.UserReadRepository
import io.realworld.user.query.UserQueryService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(
        private val userQueryService: UserQueryService,
        private val jwtService: JwtService
) : OncePerRequestFilter() {

    companion object {
        const val AUTH_HEADER = "Authorization"
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        getTokenString(request.getHeader(AUTH_HEADER))?.let { token ->
            jwtService.getSubFromToken(token)?.let { id ->
                  if (SecurityContextHolder.getContext().authentication == null) {
                      userQueryService.findBy(id)?.let { user ->
                          val authenticationToken = UsernamePasswordAuthenticationToken(user, null, emptyList())
                          authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                          SecurityContextHolder.getContext().authentication = authenticationToken
                      }
                  }
              }
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokenString(header: String?): String? {
        val split = header?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        return split?.get(1)
    }
}
