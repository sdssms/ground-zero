package com.groundzero.backend.global.common

import java.time.LocalDateTime

data class ApiResponse<T>(
    val status: Int,
    val code: String,
    val message: String,
    val data: T? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun <T> success(data: T? = null, message: String = "SUCCESS"): ApiResponse<T> {
            return ApiResponse(
                status = 200,
                code = "S200",
                message = message,
                data = data
            )
        }

        fun <T> error(status: Int, code: String, message: String): ApiResponse<T> {
            return ApiResponse(
                status = status,
                code = code,
                message = message,
                data = null
            )
        }
    }
}
