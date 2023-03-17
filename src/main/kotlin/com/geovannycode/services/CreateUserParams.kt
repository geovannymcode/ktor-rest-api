package com.geovannycode.services

data class CreateUserParams(
    val fullName: String,
    val email: String,
    val password: String,
    val avatar: String

)
