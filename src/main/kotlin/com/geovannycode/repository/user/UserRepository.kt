package com.geovannycode.repository.user

import com.geovannycode.routes.user.CreateUserParams
import com.geovannycode.routes.user.UserLoginParams
import com.geovannycode.utils.BaseResponse

interface UserRepository {
    suspend fun registerUser(params: CreateUserParams): BaseResponse<Any>

    suspend fun getUser(id: Int): BaseResponse<Any>

    suspend fun loginUser(params: UserLoginParams): BaseResponse<Any>
}
