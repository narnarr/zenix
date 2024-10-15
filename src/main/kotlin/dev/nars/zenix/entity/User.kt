package dev.nars.zenix.entity

import dev.nars.zenix.enumeration.UserStatus
import jakarta.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(unique = true, length = 32)
    var username: String,

    @Column(columnDefinition = "TEXT")
    var password: String,

    @Column(length = 16)
    var phoneNum: String? = null,

    @Column(updatable = false)
    val isBot: Boolean = false,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var status: UserStatus = UserStatus.ACTIVE,
): BaseEntity()