package dev.nars.zenix.data.config

import com.zaxxer.hikari.HikariConfig
import dev.nars.zenix.data.config.properties.MainDsProp
import dev.nars.zenix.data.constant.DsType
import dev.nars.zenix.data.constant.EmfType
import dev.nars.zenix.data.constant.PuType
import dev.nars.zenix.data.constant.TmType
import dev.nars.zenix.data.enumeration.DataSourceType
import dev.nars.zenix.data.router.SingleDataSourceRouter
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = [
        "dev.nars.zenix.data.repository.main",
    ],
    transactionManagerRef = TmType.MAIN,
    entityManagerFactoryRef = EmfType.MAIN
)
class MainDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    fun commonHikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Primary
    @Bean(DsType.MAIN)
    fun dataSource(
        mainDsProp: MainDsProp,
        commonHikariConfig: HikariConfig,
    ): DataSource {
        return SingleDataSourceRouter(DataSourceType.MAIN, commonHikariConfig, mainDsProp)
            .also { it.afterPropertiesSet() }
            .let { LazyConnectionDataSourceProxy(it) }
    }

    @Primary
    @Bean(EmfType.MAIN)
    fun entityManagerFactory(
        @Qualifier(DsType.MAIN) dataSource: DataSource,
        jpaProperties: JpaProperties,
    ): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            setupFactory(PuType.MAIN, dataSource, jpaProperties, "dev.nars.zenix.data.entity.main")
        }
    }

    @Primary
    @Bean(TmType.MAIN)
    fun transactionManager(
        @Qualifier(EmfType.MAIN) entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager().also {
            it.entityManagerFactory = entityManagerFactory
        }
    }
}

internal fun LocalContainerEntityManagerFactoryBean.setupFactory(
    persistenceUnitName: String,
    dataSource: DataSource,
    jpaProperties: JpaProperties,
    vararg packagesToScan: String,
) {
    this.setPackagesToScan(*packagesToScan)
    this.persistenceUnitName = persistenceUnitName
    this.dataSource = dataSource
    this.jpaVendorAdapter = HibernateJpaVendorAdapter().also {
        it.setDatabase(jpaProperties.database)
        it.setDatabasePlatform(jpaProperties.databasePlatform)
        it.setShowSql(jpaProperties.isShowSql)
        it.setGenerateDdl(jpaProperties.isGenerateDdl)
    }
}
