package com.groundzero.backend.global.error

open class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)
