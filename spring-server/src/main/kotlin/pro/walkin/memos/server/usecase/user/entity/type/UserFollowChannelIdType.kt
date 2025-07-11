package pro.walkin.memos.server.usecase.user.entity.type

import org.komapper.core.spi.DataTypeConverter
import pro.walkin.memos.model.user.UserFollowChannelId
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class UserFollowChannelIdType : DataTypeConverter<UserFollowChannelId, Long> {
    override val exteriorType: KType = typeOf<UserFollowChannelId>()
    override val interiorType: KType = typeOf<Long>()

    override fun wrap(interior: Long): UserFollowChannelId = UserFollowChannelId(interior)

    override fun unwrap(exterior: UserFollowChannelId): Long = exterior.id
}
