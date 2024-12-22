package dev.nars.zenix.entity

import dev.nars.zenix.enumeration.ActiveStatus
import dev.nars.zenix.enumeration.PostType
import jakarta.persistence.*

@Entity
@Table(schema = "zenix_post")
class Post(
    @Id
    val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var type: PostType,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val user: User,

    var title: String,

    @Column(columnDefinition = "TEXT")
    var text: String,

    var viewCnt: Int = 0,
): BaseEntity()