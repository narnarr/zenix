package dev.nars.zenix.data.router

import dev.nars.zenix.data.config.properties.ShardDsProp
import dev.nars.zenix.data.enumeration.DataSourceType
import dev.nars.zenix.utils.ThreadContextUtil
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class ShardDataSourceRouter(
    shardDsProp: ShardDsProp,
    dataSourceType: DataSourceType,
): AbstractRoutingDataSource() {

    private val masterKeyAndSlaveKeys: List<Pair<String, RoundRobin<String>>>

    init {
        val targetDataSources: MutableMap<Any, Any> = mutableMapOf()

        masterKeyAndSlaveKeys = shardDsProp.shards.mapIndexed { shardIdx, mhaDsProp ->
            val masterKey = mhaDsProp.master.let { master ->
                dataSourceType.generateLookUpKey(master.name, shardIdx).also {
                    targetDataSources[it] = master.toHikariConfig(dataSourceType, null)
                }
            }

            val slaveKeys = mhaDsProp.slaves.mapIndexed { slaveIdx, slave ->
                dataSourceType.generateLookUpKey(slave.name, shardIdx).also {
                    targetDataSources[it] = slave.toHikariConfig(dataSourceType, slaveIdx)
                }
            }.let {
                RoundRobin(it)
            }

            masterKey to slaveKeys
        }

        setTargetDataSources(targetDataSources)
    }

    override fun determineCurrentLookupKey(): Any {
        val shardInfo = ThreadContextUtil.getShardInfo()
        val masterAndSlaves = ThreadContextUtil.getShardInfo()
            .let { shardInfo?.shardKey ?: 0 }
            .let { masterKeyAndSlaveKeys[calcShardIdx(it)] }

        val isReadOnlyThread: Boolean = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
        val hasSlave = masterAndSlaves.second.isNotEmpty()

        return when (isReadOnlyThread && hasSlave) {
            true -> masterAndSlaves.second.next()
            else -> masterAndSlaves.first
        }
    }

    private fun calcShardIdx(shardKey: Long): Int {
        return shardKey.toInt()
    }

}