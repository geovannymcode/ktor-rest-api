package com.geovannycode.services.story

import com.geovannycode.db.DatabaseFactory
import com.geovannycode.db.DatabaseFactory.dbQuery
import com.geovannycode.db.extension.toStory
import com.geovannycode.db.extension.toStoryJoinedWithUser
import com.geovannycode.entity.StoryTable
import com.geovannycode.entity.UserTable
import com.geovannycode.models.Story
import com.geovannycode.models.common.PaginatedResult
import com.geovannycode.routes.story.StoryParams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

class DefaultStoryService : StoryService {
    override suspend fun get(id: Int): Story? {
        val storyRow = DatabaseFactory.dbQuery { StoryTable.select { StoryTable.id eq id }.first() }
        return storyRow.toStory()
    }

    override suspend fun getMyStories(userId: Int, page: Int, limit: Int): PaginatedResult<Story> {
        var pageCount: Long = 0
        var nextPage: Long? = null
        val stories = DatabaseFactory.dbQuery {
            StoryTable.innerJoin(UserTable, { UserTable.id }, { StoryTable.userId })
                .select { StoryTable.userId eq userId }.orderBy(StoryTable.createdAt, SortOrder.DESC).also {
                    pageCount = it.count() / limit
                    if (page < pageCount) {
                        nextPage = page + 1L
                    }
                }.limit(limit, (limit * page).toLong())
                .mapNotNull { it.toStoryJoinedWithUser() }
        }
        return PaginatedResult(pageCount, nextPage, stories)
    }

    override suspend fun getAllStories(page: Int, limit: Int): PaginatedResult<Story> {

        var pageCount: Long = 0
        var nextPage: Long? = null
        val stories = DatabaseFactory.dbQuery {
            StoryTable
                .innerJoin(UserTable, { UserTable.id }, { StoryTable.userId })
                .selectAll().orderBy(StoryTable.createdAt, SortOrder.DESC).also {
                    pageCount = it.count() / limit
                    if (page < pageCount)
                        nextPage = page + 1L
                }.limit(limit, (limit * page).toLong())
                .mapNotNull { it.toStoryJoinedWithUser() }
        }
        return PaginatedResult(pageCount, nextPage, stories)
    }

    override suspend fun add(storyParams: StoryParams): Story? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = StoryTable.insert {
                it[userId] = storyParams.userId
                it[title] = storyParams.title
                it[content] = storyParams.content
                it[isDraft] = storyParams.isDraft
            }
        }
        return statement?.resultedValues?.get(0).toStory()
    }
}
