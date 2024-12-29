package dev.nars.zenix.data.config.properties

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.nars.zenix.data.enumeration.DataSourceType
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.datasource.main")
data class MainDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    override val master: DsConnectionProp,
    override val slaves: List<DsConnectionProp>,
): SingleDsProp(driverClassName, username, password, master, slaves)

@ConfigurationProperties("spring.datasource.conv")
data class ConvDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    val shardJson: String,
): ShardDsProp(driverClassName, username, password, shardJson)

@ConfigurationProperties("spring.datasource.post")
data class PostDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    val shardJson: String,
): ShardDsProp(driverClassName, username, password, shardJson)

//

open class SingleDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    override val master: DsConnectionProp,
    override val slaves: List<DsConnectionProp>,
): MhaDsProp(master, slaves), DataSourceable

open class ShardDsProp(
    override var driverClassName: String,
    override val username: String,
    override val password: String,
    val shards: List<MhaDsProp>,
): DataSourceable {

    constructor(driverClassName: String, username: String, password: String, shardJson: String)
            : this(driverClassName, username, password, from(shardJson))

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
        fun toHikariDataSource(
            dataSourceType: DataSourceType,
            commonHikariConfig: HikariConfig,
            dataSourceable: DataSourceable,
            slaveIdx: Int?,
        ): HikariDataSource {
            return HikariConfig().also {
                commonHikariConfig.copyStateTo(it)

                it.poolName = dataSourceType.generatePoolName(slaveIdx)
                it.driverClassName = dataSourceable.driverClassName
                it.jdbcUrl = url
                it.username = dataSourceable.username
                it.password = dataSourceable.password
            }.let {
                HikariDataSource(it)
            }
        }
    }
}