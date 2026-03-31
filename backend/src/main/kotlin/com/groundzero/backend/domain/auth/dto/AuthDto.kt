package com.groundzero.backend.domain.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class TokenInfo(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)

data class SignupRequest(
    @field:Email
    val email: String,
    
    @field:NotBlank
    val password: String,
    
    @field:NotBlank
    val name: String
)

data class LoginRequest(
    @field:NotBlank
    val email: String,
    
    @field:NotBlank
    val password: String
)

data class RefreshRequest(
    @field:NotBlank
    val refreshToken: String
)
