package dev.nars.zenix.entity

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    @CreatedDate
    val insertedAt: ZonedDateTime = ZonedDateTime.now(),
    @LastModifiedDate
    var updatedAt: ZonedDateTime = ZonedDateTime.now(),
) {
}