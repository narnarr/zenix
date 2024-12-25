package dev.nars.zenix.data.repository.main

import dev.nars.zenix.data.entity.main.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>