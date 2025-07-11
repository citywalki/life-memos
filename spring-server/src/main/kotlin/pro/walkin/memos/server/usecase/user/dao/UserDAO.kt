package pro.walkin.memos.server.usecase.user.dao

import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Component
import pro.walkin.memos.model.user.User
import pro.walkin.memos.server.usecase.user.entity.user

@Component
class UserDAO(
    private val db: R2dbcDatabase,
) {
    private val u = Meta.user

    suspend fun insertUser(user: User): User {
        val query = QueryDsl.insert(u).single(user)

        return db.runQuery(query)
    }
}
