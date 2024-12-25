package dev.nars.zenix.enumeration

enum class ConversationType(
    val title: String,
    val code: Int,
) {
    DM("일대일 대화방", 1),
    IM("나와의 대화방", 3),
    GROUP("단체 대화방", 2),
    BIZ_GROUP("비즈니스 대화방", 4),
}