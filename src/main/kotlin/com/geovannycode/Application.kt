package com.geovannycode

import com.geovannycode.Security.configureSecurity
import com.geovannycode.config.configureContentNegotiation
import com.geovannycode.config.configureDatabase
import com.geovannycode.config.configureRouting
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureDatabase()
    configureContentNegotiation()
    configureSecurity()
    configureRouting()
}
