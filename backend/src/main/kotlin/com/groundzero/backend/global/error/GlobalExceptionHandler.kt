package com.groundzero.backend.global.error

import com.groundzero.backend.global.common.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 비즈니스 로직 예외 처리
     */
    @ExceptionHandler(BusinessException::class)
    protected fun handleBusinessException(e: BusinessException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("BusinessException: {}", e.message)
        val response = ApiResponse.error<Unit>(
            status = e.errorCode.status.value(),
            code = e.errorCode.code,
            message = e.errorCode.message
        )
        return ResponseEntity(response, e.errorCode.status)
    }

    /**
     * 입력값 검증 예외 처리 (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("MethodArgumentNotValidException: {}", e.message)
        val errorCode = ErrorCode.INVALID_INPUT_VALUE
        val message = e.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: errorCode.message
        val response = ApiResponse.error<Unit>(
            status = errorCode.status.value(),
            code = errorCode.code,
            message = message
        )
        return ResponseEntity(response, errorCode.status)
    }

    /**
     * 기타 모든 예외 처리 (예상치 못한 에러)
     */
    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        log.error("Exception: ", e)
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        val response = ApiResponse.error<Unit>(
            status = errorCode.status.value(),
            code = errorCode.code,
            message = errorCode.message
        )
        return ResponseEntity(response, errorCode.status)
    }
}
