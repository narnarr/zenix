package dev.nars.zenix.data.config

import com.zaxxer.hikari.HikariConfig
import dev.nars.zenix.data.config.properties.PostDsProp
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
        "dev.nars.zenix.data.repository.post",
    ],
    transactionManagerRef = TmType.POST,
    entityManagerFactoryRef = EmfType.POST
)
class PostDataSourceConfig {

    @Bean(DsType.POST)
    fun dataSource(
        postDsProp: PostDsProp,
        commonHikariConfig: HikariConfig,
    ): DataSource {
        return ShardDataSourceRouter(DataSourceType.POST, commonHikariConfig, postDsProp)
            .also { it.afterPropertiesSet() }
            .let { LazyConnectionDataSourceProxy(it) }
    }

    @Bean(EmfType.POST)
    fun entityManagerFactory(
        @Qualifier(DsType.POST) dataSource: DataSource,
        jpaProperties: JpaProperties,
    ): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            setupFactory(PuType.POST, dataSource, jpaProperties, "dev.nars.zenix.data.entity.post")
        }
    }

    @Bean(TmType.POST)
    fun transactionManager(
        @Qualifier(EmfType.POST) entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager().also {
            it.entityManagerFactory = entityManagerFactory
        }
    }
}