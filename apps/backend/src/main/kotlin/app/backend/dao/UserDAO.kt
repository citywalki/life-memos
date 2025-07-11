package app.backend.dao

import app.backend.dao.def.user
import model.user.Email
import model.user.User
import model.user.UserId
import model.user.UserName
import model.user.UserRole
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.operator.count
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase

class UserDAO(
    private val db: R2dbcDatabase,
) {
    private val u = Meta.user

    suspend fun findUser(id: UserId): User? {
        val query = QueryDsl.from(u).where {
            u.id eq id
        }.singleOrNull()

        return db.runQuery(query)
    }

    suspend fun findUser(username: UserName): User? {
        val query = QueryDsl.from(u).where {
            u.username eq username
        }.singleOrNull()

        return db.runQuery(query)
    }

    suspend fun findUser(email: Email): User? {
        val query = QueryDsl.from(u).where {
            u.email eq email
        }.singleOrNull()

        return db.runQuery(query)
    }

    suspend fun countUser(role: UserRole): Long {
        val query = QueryDsl.from(u).where {
            u.role eq role
        }.select(count(u.id)).map { it ?: 0 }

        return db.runQuery(query)
    }

    suspend fun insertUser(user: User): User {
        val query = QueryDsl.insert(u).single(user)

        return db.runQuery(query)
    }
}
