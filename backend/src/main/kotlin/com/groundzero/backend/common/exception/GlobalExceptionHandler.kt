package com.groundzero.backend.common.exception

import com.groundzero.backend.common.dto.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(BusinessException::class)
    protected fun handleBusinessException(e: BusinessException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("BusinessException", e)
        val errorCode = e.errorCode
        return ResponseEntity.status(errorCode.status)
            .body(ApiResponse.error(errorCode.code, e.message ?: errorCode.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("MethodArgumentNotValidException", e)
        val errorCode = ErrorCode.INVALID_INPUT_VALUE
        val bindingResult = e.bindingResult
        val message = bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity.status(errorCode.status)
            .body(ApiResponse.error(errorCode.code, message))
    }

    @ExceptionHandler(AccessDeniedException::class)
    protected fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("AccessDeniedException", e)
        val errorCode = ErrorCode.ACCESS_DENIED
        return ResponseEntity.status(errorCode.status)
            .body(ApiResponse.error(errorCode.code, errorCode.message))
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        log.error("Exception", e)
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(errorCode.status)
            .body(ApiResponse.error(errorCode.code, errorCode.message))
    }
}
