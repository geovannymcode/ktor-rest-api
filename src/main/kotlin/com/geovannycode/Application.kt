package com.geovannycode

import com.geovannycode.db.DatabaseFactory
import com.geovannycode.repository.DefaultUserRepository
import com.geovannycode.repository.UserRepository
import com.geovannycode.routes.userRoutes
import com.geovannycode.services.DefaultUserService
import com.geovannycode.services.UserService
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init()
 

    val service: UserService = DefaultUserService()
    val repository: UserRepository = DefaultUserRepository(service)
    userRoutes(repository)
}
