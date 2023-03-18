package com.geovannycode.services

import com.geovannycode.models.User
import com.geovannycode.routes.user.CreateUserParams

interface UserService {
    suspend fun registerUser(params: CreateUserParams): User?
    suspend fun findUserByEmail(email: String): User?
    suspend fun getUser(id: Int): User?
    suspend fun loginUser(email: String, password: String): User?
}
