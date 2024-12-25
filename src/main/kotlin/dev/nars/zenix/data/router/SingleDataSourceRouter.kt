package dev.nars.zenix.data.router

import dev.nars.zenix.data.config.properties.SingleDsProp
import dev.nars.zenix.data.enumeration.DataSourceType
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class SingleDataSourceRouter(
    singleDsProp: SingleDsProp,
    dataSourceType: DataSourceType,
) : AbstractRoutingDataSource() {

    private val masterKey: String
    private val slaveKeys: RoundRobin<String>

    init {
        val targetDataSources: MutableMap<Any, Any> = mutableMapOf()

        masterKey = singleDsProp.master.let { master ->
            dataSourceType.generateLookUpKey(master.name, null).also {
                targetDataSources[it] = master.toHikariConfig(dataSourceType, null)
            }
        }

        slaveKeys = singleDsProp.slaves.mapIndexed { slaveIdx, slave ->
            dataSourceType.generateLookUpKey(slave.name, null).also {
                targetDataSources[it] = slave.toHikariConfig(dataSourceType, slaveIdx)
            }
        }.let {
            RoundRobin(it)
        }

        setTargetDataSources(targetDataSources)
    }

    override fun determineCurrentLookupKey(): Any {
        val isReadOnlyThread: Boolean = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
        val hasSlave = slaveKeys.isNotEmpty()

        return when(isReadOnlyThread && hasSlave) {
            true -> slaveKeys.next()
            else -> masterKey
        }
    }
}