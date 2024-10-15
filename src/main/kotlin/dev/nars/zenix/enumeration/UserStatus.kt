package dev.nars.zenix.enumeration

enum class UserStatus(
    val title: String,
) {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    CANCELED("탈퇴"),
    BLOCKED("정지"),
}