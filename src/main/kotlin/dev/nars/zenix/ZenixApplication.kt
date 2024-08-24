package dev.nars.zenix

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ZenixApplication

fun main(args: Array<String>) {
	runApplication<ZenixApplication>(*args)
}
