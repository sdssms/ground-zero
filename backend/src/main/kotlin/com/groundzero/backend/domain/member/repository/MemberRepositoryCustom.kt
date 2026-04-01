package com.groundzero.backend.domain.member.repository

import com.groundzero.backend.domain.member.dto.MemberSearchCondition
import com.groundzero.backend.domain.member.entity.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MemberRepositoryCustom {
    fun search(condition: MemberSearchCondition, pageable: Pageable): Page<Member>
}
