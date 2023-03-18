package com.geovannycode.services.story

import com.geovannycode.db.DatabaseFactory
import com.geovannycode.db.DatabaseFactory.dbQuery
import com.geovannycode.db.extension.toStory
import com.geovannycode.entity.StoryTable
import com.geovannycode.models.Story
import com.geovannycode.models.common.PaginatedResult
import com.geovannycode.routes.story.StoryParams
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class DefaultStoryService : StoryService {
    override suspend fun get(id: Int): Story? {
        val storyRow = dbQuery { StoryTable.select { StoryTable.id eq id }.first() }
        return storyRow.toStory()
    }

    override suspend fun getMyStories(userId: Int, page: Int, limit: Int): PaginatedResult<Story> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStories(page: Int, limit: Int): PaginatedResult<Story> {
        TODO("Not yet implemented")
    }

    override suspend fun getLikedStories(userId: Int): List<Story> {
        TODO("Not yet implemented")
    }

    override suspend fun add(storyParams: StoryParams): Story? {
        var statement: InsertStatement<Number>?=null
        DatabaseFactory.dbQuery {
            statement = StoryTable.insert {
                it[userId]=storyParams.userId
                it[title]=storyParams.title
                it[content]=storyParams.content
                it[isDraft]=storyParams.isDraft
            }
        }
        return statement?.resultedValues?.get(0).toStory()
    }

    override suspend fun update(id: Int, storyParams: StoryParams): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(storyId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun like(userId: Int, storyId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun comment(userId: Int, storyId: Int, comment: String): Boolean {
        TODO("Not yet implemented")
    }
}
