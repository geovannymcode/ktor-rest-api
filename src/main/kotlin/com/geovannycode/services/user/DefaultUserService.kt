package com.geovannycode.services.user

import com.geovannycode.Security.hash
import com.geovannycode.db.DatabaseFactory.dbQuery
import com.geovannycode.db.extension.toUser
import com.geovannycode.entity.UserTable
import com.geovannycode.models.User
import com.geovannycode.routes.user.CreateUserParams
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class DefaultUserService : UserService {
    override suspend fun registerUser(params: CreateUserParams): User? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = UserTable.insert {
                it[email] = params.email
                it[password] = hash(params.password)
                it[fullName] = params.fullName
                it[avatar] = params.avatar
            }
        }
        return statement?.resultedValues?.get(0).toUser()
    }

    override suspend fun findUserByEmail(email: String): User? {
        val user = dbQuery {
            UserTable.select {
                UserTable.email.eq(email)
            }.map { it.toUser() }.singleOrNull()
        }
        return user
    }

    override suspend fun getUser(id: Int): User? {
        val userRow = dbQuery { UserTable.select { UserTable.id eq id }.first() }
        return userRow.toUser()
    }

    override suspend fun loginUser(email: String, password: String): User? {
        val hashedPassword = hash(password)
        val userRow = dbQuery { UserTable.select { UserTable.email eq email and (UserTable.password eq hashedPassword) }.firstOrNull() }
        return userRow.toUser()
    }
}
