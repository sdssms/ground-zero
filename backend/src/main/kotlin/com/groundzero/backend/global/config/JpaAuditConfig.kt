package com.groundzero.backend.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@Configuration
class JpaAuditConfig {

    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware {
            // TODO: Spring Security 적용 시 아래와 같이 Authentication 객체에서 로그인 유저 ID 추출
            // val authentication = SecurityContextHolder.getContext().authentication
            // if (authentication == null || !authentication.isAuthenticated || authentication.principal == "anonymousUser") {
            //     return@AuditorAware Optional.empty()
            // }
            // return@AuditorAware Optional.of(authentication.name)

            // 임시로 시스템(system) 식별자 반환
            Optional.of("system")
        }
    }
}
