package pro.walkin.memos.server.usecase.user.entity.type

import org.komapper.core.spi.DataTypeConverter
import pro.walkin.memos.model.user.UserChannelId
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class UserChannelIdType : DataTypeConverter<UserChannelId, Long> {
    override val exteriorType: KType = typeOf<UserChannelId>()
    override val interiorType: KType = typeOf<Long>()

    override fun wrap(interior: Long): UserChannelId = UserChannelId(interior)

    override fun unwrap(exterior: UserChannelId): Long = exterior.value.toLong()
}
