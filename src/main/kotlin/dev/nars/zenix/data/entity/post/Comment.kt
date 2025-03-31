package dev.nars.zenix.data.entity.post

import dev.nars.zenix.data.entity.BaseEntity
import dev.nars.zenix.data.entity.main.User
import dev.nars.zenix.data.enumeration.CommentType
import jakarta.persistence.*

@Entity
class Comment(
    @Id
    val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val type: CommentType,

    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val sourceComment: Comment? = null,

    @Column(columnDefinition = "TEXT")
    var text: String,
): BaseEntity()