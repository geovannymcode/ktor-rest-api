package com.geovannycode.models

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Story(
    val id: Int,
    val user: User? = null,
    val title: String,
    val content: String,
    val isDraft: Boolean = true,
    val createdAt: String,
)
