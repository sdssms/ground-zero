package com.groundzero.backend.domain.user.entity

import com.groundzero.backend.global.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, length = 100)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false, length = 50)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = Role.ROLE_USER,

    @Column(length = 500)
    var refreshToken: String? = null

) : BaseEntity() {

    fun updateRefreshToken(token: String?) {
        this.refreshToken = token
    }
}
