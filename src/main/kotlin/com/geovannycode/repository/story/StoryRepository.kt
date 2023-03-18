package com.geovannycode.repository.story

import com.geovannycode.routes.story.StoryParams
import com.geovannycode.utils.BaseResponse

interface StoryRepository {
    suspend fun getMyStories(userId: Int, page: Int, limit: Int): BaseResponse<Any>
    suspend fun getAllStories(page: Int, limit: Int): BaseResponse<Any>
    suspend fun add(storyParams: StoryParams): BaseResponse<Any>
    suspend fun update(id: Int, storyParams: StoryParams): BaseResponse<Any>
    suspend fun delete(storyId: Int): BaseResponse<Any>

    // suspend fun like(userId: Int, storyId: Int): BaseResponse<Any>
    suspend fun comment(userId: Int, storyId: Int, comment: String): BaseResponse<Any>
    suspend fun getComments(storyId: Int): BaseResponse<Any>
}
