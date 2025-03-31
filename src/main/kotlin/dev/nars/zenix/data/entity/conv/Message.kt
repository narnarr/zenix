package dev.nars.zenix.data.entity.conv

import dev.nars.zenix.data.entity.BaseEntity
import dev.nars.zenix.data.entity.main.User
import dev.nars.zenix.data.enumeration.MessageType
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(schema = "zenix_conv")
class Message(
    @Id
    val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var type: MessageType,

    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val conversation: Conversation,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var sourceMessage: Message? = null,

    @Column(columnDefinition = "TEXT")
    val text: String,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    val blocks: String? = null,
): BaseEntity()