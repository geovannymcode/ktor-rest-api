package com.geovannycode.db.extension

import com.geovannycode.entity.StoryTable
import com.geovannycode.entity.UserTable
import com.geovannycode.models.Story
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

fun ResultRow?.toStory(): Story? {
    return if (this == null) {
        null
    } else {
        Story(
            id = this[StoryTable.id],
            title = this[StoryTable.title],
            content = this[StoryTable.content],
            isDraft = this[StoryTable.isDraft],
            createdAt = this[StoryTable.createdAt].toString(),
        )
    }
}

fun ResultRow.toStoryJoinedWithUser(): Story? {
    return if (this == null) {
        null
    } else {
        Story(
            id = this[StoryTable.id],
            user = User(
                id = this[UserTable.id],
                fullName = this[UserTable.fullName],
                avatar = this[UserTable.avatar],
                email = this[UserTable.email],
                createdAt = this[UserTable.createdAt].toString(),
            ),
            title = this[StoryTable.title],
            content = this[StoryTable.content],
            isDraft = this[StoryTable.isDraft],
            createdAt = this[StoryTable.createdAt].toString(),
        )
    }
}
