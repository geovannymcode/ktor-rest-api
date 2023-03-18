package com.geovannycode.routes.story

import com.geovannycode.repository.story.StoryRepository
import com.geovannycode.routes.DEFAULT_LIMIT_SIZE
import com.geovannycode.routes.DEFAULT_PAGE_START
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.storyRoutes(storyRepository: StoryRepository) {
    routing {
        authenticate {
            route("story") {
                post("add") {
                    val params = call.receive<StoryParams>()
                    val result = storyRepository.add(params)
                    call.respond(result.statusCode, result)
                }
                get("my/{page}") {
                    val page = call.parameters["page"]?.toIntOrNull() ?: DEFAULT_PAGE_START
                    val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: DEFAULT_LIMIT_SIZE
                    val result = storyRepository.getAllStories(page, limit)
                    call.respond(result.statusCode, result)
                }
            }
        }
    }
}
