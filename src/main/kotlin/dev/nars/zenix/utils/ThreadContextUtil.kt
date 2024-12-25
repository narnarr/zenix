package dev.nars.zenix.utils

import dev.nars.zenix.data.enumeration.DataSourceType

object ThreadContextUtil {
    private val holder = ThreadLocal<ThreadContext>()

    class ThreadContext(
        var shardInfo: ShardInfo?,
    )

    fun getShardInfo(): ShardInfo? {
        return currentThreadContext().shardInfo
    }

    fun setShardInfo(
        dataSourceType: DataSourceType,
        shardKey: Long
    ) {
        currentThreadContext().shardInfo = ShardInfo(dataSourceType, shardKey)
    }

    fun clearShardInfo() {
        currentThreadContext().shardInfo = null
    }

    private fun currentThreadContext(): ThreadContext {
        return holder.get()
            ?: ThreadContext(null).also { holder.set(it) }
    }
}

data class ShardInfo(
    var dataSourceType: DataSourceType,
    var shardKey: Long,
)