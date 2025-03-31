package dev.nars.zenix.data.entity.post

import dev.nars.zenix.data.entity.BaseEntity
import dev.nars.zenix.data.enumeration.PostType
import jakarta.persistence.*

@Entity
@Table(schema = "zenix_post")
class Post(
    @Id
    val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var type: PostType,

    val userId: Long,

    var title: String,

    @Column(columnDefinition = "TEXT")
    var text: String,

    var viewCnt: Int = 0,
): BaseEntity()