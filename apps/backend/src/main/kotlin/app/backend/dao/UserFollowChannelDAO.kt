package app.backend.dao

import app.backend.dao.def.userFollowChannel
import model.user.UserFollowChannel
import model.user.UserId
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase

class UserFollowChannelDAO(
    private val database: R2dbcDatabase,
) {
    val uf = Meta.userFollowChannel
    suspend fun findFollowChannels(id: UserId, limit: Int? = null): List<UserFollowChannel> {
        var query = QueryDsl.from(uf).where {
            uf.userId eq id
        }

        query = limit?.let {
            query.limit(it)
        } ?: query

        return database.runQuery(query)
    }
}
