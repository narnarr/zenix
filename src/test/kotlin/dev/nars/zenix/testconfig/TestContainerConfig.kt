package dev.nars.zenix.testconfig

import dev.nars.zenix.utils.Profiles.TEST
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

@Configuration
@Profile(TEST)
abstract class TestContainerConfig {

    companion object {

        @Container
        private val mySql = MySQLContainer<Nothing>("mysql:8.0.32")
            .apply {
                withDatabaseName("zenix")
                withUsername("root")
                withPassword("")
                //withInitScript("schema/some_ddl.sql")
            }

        @Container
        private var redis = GenericContainer<Nothing>(DockerImageName.parse("redis:7.4.0"))
            .apply {
                withExposedPorts(16379)
            }
    }
}