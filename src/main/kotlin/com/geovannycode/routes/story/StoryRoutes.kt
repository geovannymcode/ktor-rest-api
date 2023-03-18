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

                get("all/{page}") {
                    val page = call.parameters["page"]?.toIntOrNull() ?: DEFAULT_PAGE_START
                    val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: DEFAULT_LIMIT_SIZE
                    val result = storyRepository.getAllStories(page, limit)
                    call.respond(result.statusCode, result)
                }

                put("update/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull() ?: -1
                    val params = call.receive<StoryParams>()
                    val result = storyRepository.update(id, params)
                    call.respond(result.statusCode, result)
                }

                delete("delete/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull() ?: -1
                    val result = storyRepository.delete(id)
                    call.respond(result.statusCode, result)
                }

                get("{story_id}/comments") {
                    val storyId = call.parameters["story_id"]?.toIntOrNull() ?: -1
                    val result = storyRepository.getComments(storyId)
                    call.respond(result.statusCode, result)
                }

                post("add/comments") {
                    val userId = call.parameters["userId"]?.toIntOrNull() ?: -1
                    val storyId = call.parameters["storyId"]?.toIntOrNull() ?: -1
                    val comment = call.parameters["comment"].toString()
                    val result = storyRepository.comment(userId, storyId, comment)
                    call.respond(result.statusCode, result)
                }

                post("add/likes") {
                    val userId = call.parameters["userId"]?.toIntOrNull() ?: -1
                    val storyId = call.parameters["storyId"]?.toIntOrNull() ?: -1
                    val result = storyRepository.like(userId, storyId)
                    call.respond(result.statusCode, result)
                }
            }
        }
    }
}
