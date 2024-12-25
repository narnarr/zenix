package dev.nars.zenix.enumeration

enum class MessageType(
    val title: String,
    val code: Int,
) {
    NORMAL("일반", 1),
    NOTICE("공지", 2),
    REPLY("답장", 3),
    INFO("정보", 4),
}