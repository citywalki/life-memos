package app.backend.dao.type

import model.user.UserId
import org.komapper.core.spi.DataTypeConverter
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class UserIdType : DataTypeConverter<UserId, Long> {
    override val exteriorType: KType = typeOf<UserId>()
    override val interiorType: KType = typeOf<Long>()

    override fun wrap(interior: Long): UserId {
        return UserId(interior)
    }

    override fun unwrap(exterior: UserId): Long {
        return exterior.value
    }
}
