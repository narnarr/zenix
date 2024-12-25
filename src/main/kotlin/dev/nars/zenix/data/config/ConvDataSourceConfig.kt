package dev.nars.zenix.data.config

import dev.nars.zenix.data.config.properties.ConvDsProp
import dev.nars.zenix.data.constant.DsType
import dev.nars.zenix.data.constant.EmfType
import dev.nars.zenix.data.constant.PuType
import dev.nars.zenix.data.constant.TmType
import dev.nars.zenix.data.enumeration.DataSourceType
import dev.nars.zenix.data.router.ShardDataSourceRouter
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = [
        "dev.nars.zenix.data.repository.conv",
    ],
    transactionManagerRef = TmType.CONV,
    entityManagerFactoryRef = EmfType.CONV
)
class ConvDataSourceConfig {

    @Bean(DsType.CONV)
    fun dataSource(convDsProp: ConvDsProp): DataSource {
        return ShardDataSourceRouter(convDsProp, DataSourceType.CONV)
            .also { it.afterPropertiesSet() }
            .let { LazyConnectionDataSourceProxy(it) }
    }

    @Bean(EmfType.CONV)
    fun entityManagerFactory(
        @Qualifier(DsType.CONV) dataSource: DataSource,
        jpaProperties: JpaProperties,
    ): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            setupFactory(PuType.CONV, dataSource, jpaProperties, "dev.nars.zenix.data.entity.conv")
        }
    }

    @Bean(TmType.CONV)
    fun transactionManager(
        @Qualifier(EmfType.CONV) entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager().also {
            it.entityManagerFactory = entityManagerFactory
        }
    }
}