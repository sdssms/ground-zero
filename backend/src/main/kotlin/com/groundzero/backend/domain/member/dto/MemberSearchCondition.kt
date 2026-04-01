package com.groundzero.backend.domain.member.dto

import java.time.LocalDateTime

data class MemberSearchCondition(
    val name: String? = null,
    val email: String? = null,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null
)
