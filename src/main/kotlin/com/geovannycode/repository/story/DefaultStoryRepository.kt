package com.geovannycode.repository.story

import com.geovannycode.services.story.StoryService

class DefaultStoryRepository(private val storyService: StoryService) : StoryRepository{

}
