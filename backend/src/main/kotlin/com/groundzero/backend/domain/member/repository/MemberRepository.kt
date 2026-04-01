package com.groundzero.backend.domain.member.repository

import com.groundzero.backend.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberRepositoryCustom
