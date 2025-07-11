package pro.walkin.memos.server.usecase.user.entity.type

import org.komapper.core.spi.DataTypeConverter
import pro.walkin.memos.model.user.UserId
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class UserIdType : DataTypeConverter<UserId, Long> {
    override val exteriorType: KType = typeOf<UserId>()
    override val interiorType: KType = typeOf<Long>()

    override fun wrap(interior: Long): UserId = UserId(interior)

    override fun unwrap(exterior: UserId): Long = exterior.value
}
