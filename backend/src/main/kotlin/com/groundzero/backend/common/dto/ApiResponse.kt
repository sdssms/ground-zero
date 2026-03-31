package com.groundzero.backend.common.dto

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorResponse? = null
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(success = true, data = data)
        }

        fun <T> error(code: String, message: String): ApiResponse<T> {
            return ApiResponse(success = false, error = ErrorResponse(code, message))
        }
    }
}

data class ErrorResponse(
    val code: String,
    val message: String
)
