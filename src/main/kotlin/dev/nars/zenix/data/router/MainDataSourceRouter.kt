package dev.nars.zenix.data.router

import com.zaxxer.hikari.HikariDataSource
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager
import java.util.concurrent.atomic.AtomicInteger

class MainDataSourceRouter(
    dataSources: Map<String, HikariDataSource>,
) : AbstractRoutingDataSource() {

    private lateinit var masterKey: String
    private lateinit var slaveKeys: MutableList<String>

    private val counter = AtomicInteger(0)

    init {
        setTargetDataSources(
            dataSources.entries
                .associateTo(mutableMapOf()) { it.key as Any to it.value as Any }
        )

        dataSources.keys.onEach {
            when (it.startsWith("m")) {
                true -> masterKey = it
                else -> slaveKeys.add(it)
            }
        }

        slaveKeys.sortBy { it[1].digitToInt() }
    }

    override fun determineCurrentLookupKey(): Any? {
        val slaveIdx = counter.getAndUpdate { (it + 1) % slaveKeys.size }

        val isReadOnlyThread: Boolean = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
        val hasSlave = slaveKeys.size > 0
        return when(isReadOnlyThread && hasSlave) {
            true -> slaveKeys[slaveIdx]
            else -> masterKey
        }
    }
}