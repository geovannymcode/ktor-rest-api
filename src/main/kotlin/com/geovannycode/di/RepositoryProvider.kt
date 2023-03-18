package com.geovannycode.di

import com.geovannycode.repository.story.DefaultStoryRepository
import com.geovannycode.repository.user.DefaultUserRepository
import com.geovannycode.repository.user.UserRepository
import com.geovannycode.repository.story.StoryRepository
import com.geovannycode.services.story.DefaultStoryService
import com.geovannycode.services.user.DefaultUserService

object RepositoryProvider {
    fun provideUserRepository(): UserRepository = DefaultUserRepository(DefaultUserService())
    fun provideStoryRepository(): StoryRepository = DefaultStoryRepository(DefaultStoryService())

}
