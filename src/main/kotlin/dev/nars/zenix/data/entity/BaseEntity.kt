package dev.nars.zenix.data.entity

import dev.nars.zenix.data.enumeration.ActiveStatus
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var status: ActiveStatus = ActiveStatus.ACTIVE,
    @CreatedDate
    val insertedAt: ZonedDateTime = ZonedDateTime.now(),
    @LastModifiedDate
    var updatedAt: ZonedDateTime = ZonedDateTime.now(),
) {
}