package com.geovannycode.db.extension

import com.geovannycode.entity.UserTable
import com.geovannycode.models.User
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow?.toUser(): User? {
    return if (this == null) {
        null
    } else {
        User(
            id = this[UserTable.id],
            fullName = this[UserTable.fullName],
            avatar = this[UserTable.avatar],
            email = this[UserTable.email],
            createdAt = this[UserTable.createdAt].toString(),
        )
    }
}
