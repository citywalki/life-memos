package pro.walkin.memos.server.usecase.user

import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import pro.walkin.memos.model.user.UserChannel
import pro.walkin.memos.server.usecase.user.entity.userChannel

@Service
class UserChannelService(
    private val db: R2dbcDatabase,
) {
    private val c = Meta.userChannel

    suspend fun insertChannel(userChannel: UserChannel): UserChannel =
        db.runQuery {
            QueryDsl.insert(c).single(userChannel)
        }
}
