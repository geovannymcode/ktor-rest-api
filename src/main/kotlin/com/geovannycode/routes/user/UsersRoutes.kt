package com.geovannycode.routes.user

import com.geovannycode.Security.UserIdPrincipalForUser
import com.geovannycode.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.usersRoutes(repository: UserRepository) {
    routing {
        authenticate {
            route("/user") {
                get {
                    val principal = call.principal<UserIdPrincipalForUser>()
                    val result = repository.getUser(principal?.id!!)
                    call.respond(result.statusCode, result)
                }
            }
        }
    }
}
