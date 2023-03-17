package com.geovannycode.repository

import com.geovannycode.services.CreateUserParams
import com.geovannycode.services.UserService
import com.geovannycode.utils.BaseResponse

class DefaultUserRepository(private val userService: UserService) : UserRepository {
    override suspend fun registerUser(params: CreateUserParams): BaseResponse<Any> {
        return if (isEmailExist(params.email)) {
            BaseResponse.ErrorResponse(message = "Email already registered")
        }else{
            val user = userService.registerUser(params)
            if(user !=null){
                BaseResponse.SuccessResponse(data=user)
            }else{
                BaseResponse.ErrorResponse(message = "")
            }
        }
    }

    override suspend fun findUserByEmail(email: String): BaseResponse<Any> {
        TODO("Not yet implemented")
    }

    private suspend fun isEmailExist(email: String): Boolean {
        return userService.findUserByEmail(email) != null
    }
}
