package dev.nars.zenix.enumeration

enum class ActiveStatus(
    val title: String,
) {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    DELETED("삭제/탈퇴"),
    BLOCKED("신고/정지"),
}