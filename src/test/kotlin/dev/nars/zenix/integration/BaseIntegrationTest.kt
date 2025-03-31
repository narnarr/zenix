package dev.nars.zenix.integration

import dev.nars.zenix.data.constant.Profiles
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@ActiveProfiles(Profiles.TEST)
abstract class BaseIntegrationTest