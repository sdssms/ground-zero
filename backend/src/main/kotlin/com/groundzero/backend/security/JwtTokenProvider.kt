package com.groundzero.backend.security

import com.groundzero.backend.domain.auth.dto.TokenInfo
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val validityInMilliseconds: Long
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey))
    private val refreshValidityInMilliseconds = validityInMilliseconds * 14 // 14 days

    fun createToken(authentication: Authentication): TokenInfo {
        val authorities = authentication.authorities.joinToString(",") { it.authority ?: "" }
        val now = Date()
        
        val accessTokenExpiration = Date(now.time + validityInMilliseconds)
        val accessToken = Jwts.builder()
            .subject(authentication.name ?: "")
            .claim("auth", authorities)
            .issuedAt(now)
            .expiration(accessTokenExpiration)
            .signWith(key)
            .compact()

        val refreshTokenExpiration = Date(now.time + refreshValidityInMilliseconds)
        val refreshToken = Jwts.builder()
            .subject(authentication.name ?: "")
            .claim("auth", authorities)
            .issuedAt(now)
            .expiration(refreshTokenExpiration)
            .signWith(key)
            .compact()

        return TokenInfo("Bearer", accessToken, refreshToken)
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        val authoritiesString = claims["auth"]?.toString() ?: ""
        val authorities = if (authoritiesString.isBlank()) emptyList() else authoritiesString.split(",").map { SimpleGrantedAuthority(it) }
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
            !claims.payload.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}
