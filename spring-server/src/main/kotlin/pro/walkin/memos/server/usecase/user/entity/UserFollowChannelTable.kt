package pro.walkin.memos.server.usecase.user.entity

import org.komapper.annotation.KomapperCreatedAt
import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import pro.walkin.memos.model.user.UserFollowChannel

@KomapperEntityDef(UserFollowChannel::class)
@KomapperTable("memos_user_follow_channel")
data class UserFollowChannelTable(
    @KomapperId val uid: Nothing,
    @KomapperCreatedAt val followStartAt: Nothing,
)
