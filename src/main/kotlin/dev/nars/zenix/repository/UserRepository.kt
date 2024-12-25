package dev.nars.zenix.repository

import dev.nars.zenix.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>