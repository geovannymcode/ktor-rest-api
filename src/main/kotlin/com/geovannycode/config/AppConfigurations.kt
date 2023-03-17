package com.geovannycode.config

import com.geovannycode.db.DatabaseFactory
import com.geovannycode.di.RepositoryProvider
import com.geovannycode.routes.userRoutes
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun configureDatabase() {
    DatabaseFactory.init()
}

fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        jackson()
    }
}

fun Application.configureRouting(){
    userRoutes(RepositoryProvider.provideUserRepository())
}