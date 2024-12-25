package dev.nars.zenix.data.entity.embeddable

import dev.nars.zenix.data.enumeration.BizType
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class BizInfo(
    var bizName: String,

    var bizPhoneNum: String,

    var bizWebsiteUrl: String? = null,

    @Enumerated(EnumType.STRING)
    var bizType: BizType = BizType.INFLUENCER,
)