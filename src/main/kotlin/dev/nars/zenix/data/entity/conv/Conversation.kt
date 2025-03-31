package dev.nars.zenix.data.entity.conv

import dev.nars.zenix.data.entity.BaseEntity
import dev.nars.zenix.data.enumeration.ConversationType
import jakarta.persistence.*

@Entity
@Table(schema = "zenix_conv")
class Conversation(
    @Id
    val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var type: ConversationType,

    var hostUserId: Long,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    var displayMessage: Message,

    @Column(nullable = false, length = 10)
    var title: String,

    var userCnt: Int = 0,

    var profileUrl: String? = null,
): BaseEntity()