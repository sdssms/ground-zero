package com.groundzero.backend.domain.member.entity

import com.groundzero.backend.global.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "members")
class Member(
    @Column(nullable = false, length = 50)
    var name: String,

    @Column(nullable = false, unique = true, length = 100)
    var email: String,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
