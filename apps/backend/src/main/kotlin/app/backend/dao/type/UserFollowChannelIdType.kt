package app.backend.dao.type

import model.user.UserFollowChannelId
import org.komapper.core.spi.DataTypeConverter
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class UserFollowChannelIdType : DataTypeConverter<UserFollowChannelId, Long> {
    override val exteriorType: KType = typeOf<UserFollowChannelId>()
    override val interiorType: KType = typeOf<Long>()

    override fun wrap(interior: Long): UserFollowChannelId {
        return UserFollowChannelId(interior)
    }

    override fun unwrap(exterior: UserFollowChannelId): Long {
        return exterior.id
    }
}
