package com.geovannycode.di

import com.geovannycode.repository.DefaultUserRepository
import com.geovannycode.repository.UserRepository
import com.geovannycode.services.DefaultUserService

object RepositoryProvider {
    fun provideUserRepository(): UserRepository = DefaultUserRepository(DefaultUserService())
}
