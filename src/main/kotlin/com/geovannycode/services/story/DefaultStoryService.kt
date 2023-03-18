package com.geovannycode.services.story

import com.geovannycode.db.DatabaseFactory
import com.geovannycode.db.extension.toComment
import com.geovannycode.db.extension.toStory
import com.geovannycode.db.extension.toStoryJoinedWithUser
import com.geovannycode.entity.CommentTable
import com.geovannycode.entity.LikeTable
import com.geovannycode.entity.StoryTable
import com.geovannycode.entity.UserTable
import com.geovannycode.models.Comment
import com.geovannycode.models.Story
import com.geovannycode.models.common.PaginatedResult
import com.geovannycode.routes.story.StoryParams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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
                    if (page < pageCount) {
                        nextPage = page + 1L
                    }
                }.limit(limit, (limit * page).toLong())
                .mapNotNull { it.toStoryJoinedWithUser() }
        }
        return PaginatedResult(pageCount, nextPage, stories)
    }

    override suspend fun getLikedStories(userId: Int): List<Story> {
        return DatabaseFactory.dbQuery {
            val storyTable = StoryTable.alias("s")
            LikeTable.innerJoin(storyTable, { LikeTable.storyId }, { storyTable[StoryTable.id] })
                .select { LikeTable.userId eq userId }
                .mapNotNull { it.toStory() }
        }
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

    override suspend fun update(id: Int, storyParams: StoryParams): Boolean {
        var result = -1
        DatabaseFactory.dbQuery {
            result = StoryTable.update({ StoryTable.id eq id }) {
                it[userId] = storyParams.userId
                it[title] = storyParams.title
                it[content] = storyParams.content
                it[isDraft] = storyParams.isDraft
            }
        }
        return result == 1
    }

    override suspend fun delete(storyId: Int): Boolean {
        var result = -1
        DatabaseFactory.dbQuery {
            result = StoryTable.deleteWhere { StoryTable.id eq storyId }
        }
        return result == 1
    }

    override suspend fun like(userId: Int, storyId: Int): Boolean {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = LikeTable.insert {
                it[this.userId] = userId
                it[this.storyId] = storyId
            }
        }
        return statement?.resultedValues?.isNotEmpty() ?: false
    }

    override suspend fun comment(userId: Int, storyId: Int, comment: String): Boolean {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = CommentTable.insert {
                it[this.userId] = userId
                it[this.storyId] = storyId
                it[this.comment] = comment
            }
        }
        return statement?.resultedValues?.isNotEmpty() ?: false
    }

    override suspend fun getComments(storyId: Int): List<Comment> {
        return DatabaseFactory.dbQuery {
            CommentTable.select(CommentTable.storyId eq storyId).mapNotNull {
                it.toComment()
            }
        }
    }
}
