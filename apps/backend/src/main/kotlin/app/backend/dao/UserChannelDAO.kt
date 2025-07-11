package app.backend.dao

import app.backend.dao.def.userChannel
import model.user.ChannelName
import model.user.UserChannel
import model.user.UserChannelId
import model.user.UserId
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase

class UserChannelDAO(
    private val db: R2dbcDatabase,
) {
    private val c = Meta.userChannel

    suspend fun findChannels(
        userId: UserId,
        limit: Int? = null,
    ): List<UserChannel> =
        db.runQuery {
            val query =
                QueryDsl.from(c).where {
                    c.ownerUser eq userId
                }
            limit?.let {
                query.limit(limit)
            } ?: query
        }

    suspend fun insertChannel(userChannel: UserChannel): UserChannel =
        db.runQuery {
            QueryDsl.insert(c).single(userChannel)
        }

    suspend fun findChannel(
        userChannelId: UserChannelId?,
        userChannelName: ChannelName?,
    ): UserChannel? {
        if (userChannelId !== null) {
            return db.runQuery {
                QueryDsl
                    .from(c)
                    .where {
                        c.uid eq userChannelId
                    }.singleOrNull()
            }
        }

        if (userChannelName !== null) {
            return db.runQuery {
                QueryDsl
                    .from(c)
                    .where {
                        c.name eq userChannelName
                    }.singleOrNull()
            }
        }

        return null
    }
}
