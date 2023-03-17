package com.geovannycode.repository

import com.geovannycode.services.CreateUserParams
import com.geovannycode.utils.BaseResponse

interface UserRepository {
    suspend fun registerUser(params: CreateUserParams): BaseResponse<Any>
    suspend fun findUserByEmail(email: String): BaseResponse<Any>
}