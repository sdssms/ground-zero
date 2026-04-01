package com.groundzero.backend.domain.member.repository

import com.groundzero.backend.domain.member.dto.MemberSearchCondition
import com.groundzero.backend.domain.member.entity.Member
import com.groundzero.backend.domain.member.entity.QMember.member
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils

@Repository
class MemberRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : MemberRepositoryCustom {

    override fun search(condition: MemberSearchCondition, pageable: Pageable): Page<Member> {
        val content = queryFactory
            .selectFrom(member)
            .where(
                nameContains(condition.name),
                emailContains(condition.email),
                createdBetween(condition)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = queryFactory
            .select(member.count())
            .from(member)
            .where(
                nameContains(condition.name),
                emailContains(condition.email),
                createdBetween(condition)
            )
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, total)
    }

    private fun nameContains(name: String?): BooleanExpression? {
        return if (StringUtils.hasText(name)) member.name.containsIgnoreCase(name) else null
    }

    private fun emailContains(email: String?): BooleanExpression? {
        return if (StringUtils.hasText(email)) member.email.containsIgnoreCase(email) else null
    }

    private fun createdBetween(condition: MemberSearchCondition): BooleanExpression? {
        val start = condition.startDate
        val end = condition.endDate
        
        if (start != null && end != null) {
            return member.createdAt.between(start, end)
        }
        if (start != null) {
            return member.createdAt.goe(start)
        }
        if (end != null) {
            // Include up to end of the day or just the exact end boundary
            return member.createdAt.loe(end)
        }
        return null
    }
}
