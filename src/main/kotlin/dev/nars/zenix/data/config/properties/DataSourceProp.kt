package dev.nars.zenix.config.properties

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.nars.zenix.enumeration.DataSourceType
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.datasource.main")
data class MainDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    override val master: DsConnectionProp,
    override val slaves: List<DsConnectionProp>,
): SingleDsProp(driverClassName, username, password, master, slaves) {

    fun toMhaDataSources() = toMhaDataSources(DataSourceType.MAIN)
}

@ConfigurationProperties("spring.datasource.conv")
data class ConvDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    val shardJson: String,
): ShardDsProp(driverClassName, username, password, shardJson) {

    fun toMhaDataSources() = toMhaDataSources(DataSourceType.CONV)
}

@ConfigurationProperties("spring.datasource.post")
data class PostDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    val shardJson: String,
): ShardDsProp(driverClassName, username, password, shardJson) {

    fun toMhaDataSources() = toMhaDataSources(DataSourceType.POST)
}

//

open class SingleDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    override val master: DsConnectionProp,
    override val slaves: List<DsConnectionProp>,
): MhaDsProp(master, slaves), DataSourceable {

    protected fun toMhaDataSources(
        dataSourceType: DataSourceType,
    ): Map<String, HikariDataSource> {
        return toMhaHikariConfigs(dataSourceType, null).mapValues { (_, hikariConfig) ->
            hikariConfig.also {
                it.driverClassName = driverClassName
                it.username = username
                it.password = password
            }.let {
                HikariDataSource(it)
            }
        }
    }
}

open class ShardDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    val shards: List<MhaDsProp>,
): DataSourceable {

    val totalShardCnt = shards.size

    constructor(driverClassName: String, username: String, password: String, shardJson: String)
            : this(driverClassName, username, password, from(shardJson))

    protected fun toMhaDataSources(
        dataSourceType: DataSourceType,
    ): Map<String, HikariDataSource> {
        return shards.flatMapIndexed { shardIdx, shard ->
            shard.toMhaHikariConfigs(dataSourceType, shardIdx).mapValues { (_, hikariConfig) ->
                hikariConfig.also {
                    it.driverClassName = driverClassName
                    it.username = username
                    it.password = password
                }.let {
                    HikariDataSource(it)
                }
            }.entries
        }.associate {
            it.key to it.value
        }
    }

    companion object {
        private val objectMapper = ObjectMapper().registerKotlinModule()

        fun from(shardJson: String): List<MhaDsProp> {
            return objectMapper
                .readTree(shardJson)
                .path("shards")
                .map {
                    objectMapper.convertValue(it, MhaDsProp::class.java)
                }
        }
    }
}

interface DataSourceable {
    var driverClassName: String
    val username: String
    val password: String
    // shardJson
    // master and slaves
}

open class MhaDsProp(
    open val master: DsConnectionProp,
    open val slaves: List<DsConnectionProp>,
) {
    data class DsConnectionProp(
        val name: String,
        val url: String,
    ) {
        fun toHikariConfig(
            dataSourceType: DataSourceType,
            slaveIdx: Int?,
        ): HikariConfig {
            return HikariConfig().also {
                commonHikariConfig.copyStateTo(it)

                it.poolName = dataSourceType.generatePoolName(slaveIdx)
                it.jdbcUrl = url
            }
        }
    }

    fun toMhaHikariConfigs(
        dataSourceType: DataSourceType,
        shardIdx: Int?,
    ): Map<String, HikariConfig> {
        return mapOf(
            dataSourceType.generateSourceName(master.name, shardIdx) to master.toHikariConfig(dataSourceType, null),
            *slaves.mapIndexed { slaveIdx, slave ->
                dataSourceType.generateSourceName(slave.name, shardIdx) to slave.toHikariConfig(dataSourceType, slaveIdx)
            }.toTypedArray()
        )
    }

    companion object {
        lateinit var commonHikariConfig: HikariConfig
    }
}