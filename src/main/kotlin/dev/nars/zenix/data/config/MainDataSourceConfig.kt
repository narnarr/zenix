package dev.nars.zenix.data.config

import dev.nars.zenix.data.config.properties.MainDsProp
import dev.nars.zenix.data.constant.DsType
import dev.nars.zenix.data.router.MainDataSourceRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import javax.sql.DataSource

@Configuration
class MainDataSourceConfig {

    @Bean(DsType.MAIN)
    fun dataSource(mainDsProp: MainDsProp): DataSource {
        return MainDataSourceRouter(mainDsProp.toMhaDataSources())
            .also { it.afterPropertiesSet() }
            .let { LazyConnectionDataSourceProxy(it) }
    }
}