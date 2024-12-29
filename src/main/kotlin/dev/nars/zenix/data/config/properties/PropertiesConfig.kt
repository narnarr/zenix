package dev.nars.zenix.data.config.properties

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(
    "dev.nars.zenix.data.config.properties",
)
@EnableConfigurationProperties
class PropertiesConfig