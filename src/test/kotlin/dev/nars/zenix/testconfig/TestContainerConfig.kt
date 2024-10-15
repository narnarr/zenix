package dev.nars.zenix.testconfig

import dev.nars.zenix.utils.Profiles.TEST
import jakarta.persistence.PostRemove
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

@Configuration
@Profile(TEST)
class TestContainerConfig : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        applicationContext.environment.run {
            URL = getProperty("spring.datasource.url").toString()
            USERNAME = getProperty("spring.datasource.username").toString()
            PASSWORD = getProperty("spring.datasource.password").toString()
        }

        MYSQL = MySQLContainer<Nothing>("mysql:8.0.32")
            .apply {
                withDatabaseName(DATABASE_NAME)
                withUsername(USERNAME)
                withPassword(PASSWORD)
                //withInitScript("schema/some_ddl.sql")
                start()
            }

        TestPropertyValues.of(
            "spring.datasource.url=$URL",
            "spring.datasource.username=${MYSQL.username}",
            "spring.datasource.password=${MYSQL.password}",
        ).applyTo(applicationContext.environment)

        REDIS = GenericContainer<Nothing>(DockerImageName.parse("redis:7.4.0"))
            .apply {
                start()
            }
    }

    @PostRemove
    fun cleanUp() {
        MYSQL.stop()
        REDIS.stop()
    }

    companion object {
        private const val DATABASE_NAME = "zenix"
        private lateinit var URL: String
        private lateinit var USERNAME: String
        private lateinit var PASSWORD: String

        @Container
        private lateinit var MYSQL: MySQLContainer<Nothing>

        @Container
        private lateinit var REDIS: GenericContainer<Nothing>
    }
}