package dev.nars.zenix.data.config.properties

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(
    "dev.nars.zenix.config.properties",
)
//@EnableConfigurationProperties
class PropertiesConfig