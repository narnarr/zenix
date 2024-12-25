package dev.nars.zenix.data.entity

import dev.nars.zenix.data.entity.embeddable.BizInfo
import jakarta.persistence.*

@Entity
@Table(schema = "zenix_main")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(unique = true, length = 32, updatable = false)
    val username: String,

    @Column(columnDefinition = "TEXT")
    var password: String,

    @Column(unique = true, length = 32)
    var nickname: String,

    @Column(length = 16)
    var phoneNum: String? = null,

    @Column(updatable = false)
    val isBot: Boolean = false,

    var isStar: Boolean = false,

    var profileUrl: String? = null,

    var postCnt: Int = 0,

    var followerCnt: Int = 0,

    var followingCnt: Int = 0,

    @Column(length = 64)
    var description: String? = null,

    @Embedded
    var bizInfo: BizInfo? = null,
): BaseEntity()