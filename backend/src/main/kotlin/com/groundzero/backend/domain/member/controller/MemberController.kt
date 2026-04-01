package com.groundzero.backend.domain.member.controller

import com.groundzero.backend.domain.member.dto.MemberSearchCondition
import com.groundzero.backend.domain.member.entity.Member
import com.groundzero.backend.domain.member.repository.MemberRepository
import com.groundzero.backend.global.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Member API", description = "Sample member querying with generic pageable response")
@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberRepository: MemberRepository
) {

    @Operation(summary = "Search Members", description = "Querydsl based dynamic search with Pagination output")
    @GetMapping
    fun searchMembers(
        @ModelAttribute condition: MemberSearchCondition,
        @PageableDefault(size = 20, sort = ["createdAt"]) pageable: Pageable
    ): ApiResponse<Page<Member>> {
        val result = memberRepository.search(condition, pageable)
        return ApiResponse.success(result)
    }
}
