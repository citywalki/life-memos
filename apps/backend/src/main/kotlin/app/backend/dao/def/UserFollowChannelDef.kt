package app.backend.dao.def

import model.user.UserFollowChannel
import org.komapper.annotation.KomapperCreatedAt
import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntityDef(UserFollowChannel::class)
@KomapperTable("memos_user_follow_channel")
data class UserFollowChannelDef(
    @KomapperId val uid: Nothing,
    @KomapperCreatedAt val followStartAt: Nothing,
)
