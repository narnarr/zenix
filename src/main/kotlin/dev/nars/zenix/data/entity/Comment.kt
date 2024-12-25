package dev.nars.zenix.data.entity

import dev.nars.zenix.data.enumeration.CommentType
import jakarta.persistence.*

@Entity
class Comment(
    @Id
    val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val type: CommentType,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val sourceComment: Comment? = null,

    @Column(columnDefinition = "TEXT")
    var text: String,
): BaseEntity()