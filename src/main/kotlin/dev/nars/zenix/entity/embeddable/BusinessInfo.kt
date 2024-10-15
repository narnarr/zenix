package dev.nars.zenix.entity.embeddable

import dev.nars.zenix.enumeration.BusinessType
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class BusinessInfo(
    val businessName: String,

    val businessPhoneNum: String,

    val websiteUrl: String? = null,

    @Enumerated(EnumType.STRING)
    val businessType: BusinessType = BusinessType.INFLUENCER,
)