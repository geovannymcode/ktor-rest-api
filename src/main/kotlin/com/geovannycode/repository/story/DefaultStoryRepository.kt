package com.geovannycode.repository.story

import com.geovannycode.config.GENERIC_ERROR
import com.geovannycode.config.SUCCESS
import com.geovannycode.routes.story.StoryParams
import com.geovannycode.services.story.StoryService
import com.geovannycode.utils.BaseResponse

class DefaultStoryRepository(private val storyService: StoryService) : StoryRepository {
    override suspend fun getMyStories(userId: Int, page: Int, limit: Int): BaseResponse<Any> {
        return BaseResponse.SuccessResponse(data = storyService.getMyStories(userId, page, limit), message = SUCCESS)
    }

    override suspend fun getAllStories(page: Int, limit: Int): BaseResponse<Any> {
        return BaseResponse.SuccessResponse(data = storyService.getAllStories(page,limit), message = SUCCESS)
    }

    override suspend fun add(storyParams: StoryParams): BaseResponse<Any> {
        val story = storyService.add(storyParams)
        return if (story != null) {
            BaseResponse.SuccessResponse(data = story, message = SUCCESS)
        } else {
            BaseResponse.ErrorResponse(message = GENERIC_ERROR)
        }
    }
}
