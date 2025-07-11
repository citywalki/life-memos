package app.backend.dao.type

import model.user.UserChannelId
import org.komapper.core.spi.DataTypeConverter
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class UserChannelIdType : DataTypeConverter<UserChannelId, Long> {
    override val exteriorType: KType = typeOf<UserChannelId>()
    override val interiorType: KType = typeOf<Long>()

    override fun wrap(interior: Long): UserChannelId {
        return UserChannelId(interior)
    }

    override fun unwrap(exterior: UserChannelId): Long {
        return exterior.value.toLong()
    }
}
