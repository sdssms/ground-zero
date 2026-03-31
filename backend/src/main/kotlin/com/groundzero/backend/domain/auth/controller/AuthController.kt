package com.groundzero.backend.domain.auth.controller

import com.groundzero.backend.common.dto.ApiResponse
import com.groundzero.backend.common.exception.BusinessException
import com.groundzero.backend.common.exception.ErrorCode
import com.groundzero.backend.domain.auth.dto.LoginRequest
import com.groundzero.backend.domain.auth.dto.RefreshRequest
import com.groundzero.backend.domain.auth.dto.SignupRequest
import com.groundzero.backend.domain.auth.dto.TokenInfo
import com.groundzero.backend.domain.user.entity.Role
import com.groundzero.backend.domain.user.entity.User
import com.groundzero.backend.domain.user.repository.UserRepository
import com.groundzero.backend.security.JwtTokenProvider
import jakarta.validation.Valid
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody req: SignupRequest): ApiResponse<Long> {
        if (userRepository.existsByEmail(req.email)) {
            throw BusinessException(ErrorCode.INVALID_INPUT_VALUE, "Email already exists")
        }
        val user = User(
            email = req.email,
            password = passwordEncoder.encode(req.password)!!,
            name = req.name,
            role = Role.ROLE_USER
        )
        val saved = userRepository.save(user)
        return ApiResponse.success(saved.id!!)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody req: LoginRequest): ApiResponse<TokenInfo> {
        val user = userRepository.findByEmail(req.email)
            ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND, "User not found")

        if (!passwordEncoder.matches(req.password, user.password)) {
            throw BusinessException(ErrorCode.INVALID_INPUT_VALUE, "Invalid password")
        }

        val authorities = listOf(SimpleGrantedAuthority(user.role.name))
        val authentication = UsernamePasswordAuthenticationToken(user.email, "", authorities)
        
        val tokenInfo = jwtTokenProvider.createToken(authentication)
        
        user.updateRefreshToken(tokenInfo.refreshToken)
        userRepository.save(user)

        return ApiResponse.success(tokenInfo)
    }

    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody req: RefreshRequest): ApiResponse<TokenInfo> {
        if (!jwtTokenProvider.validateToken(req.refreshToken)) {
            throw BusinessException(ErrorCode.UNAUTHORIZED, "Invalid refresh token")
        }

        val auth = jwtTokenProvider.getAuthentication(req.refreshToken)
        val user = userRepository.findByEmail(auth.name ?: "")
            ?: throw BusinessException(ErrorCode.ENTITY_NOT_FOUND, "User not found")

        if (user.refreshToken != req.refreshToken) {
            throw BusinessException(ErrorCode.UNAUTHORIZED, "Refresh token mismatch")
        }

        val newTokenInfo = jwtTokenProvider.createToken(auth)
        user.updateRefreshToken(newTokenInfo.refreshToken)
        userRepository.save(user)

        return ApiResponse.success(newTokenInfo)
    }
}
