package app.backend.dao.type

import model.user.UserName
import org.komapper.core.spi.DataTypeConverter
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class UserNameType : DataTypeConverter<UserName, String> {
    override val exteriorType: KType = typeOf<UserName>()
    override val interiorType: KType = typeOf<String>()
    override fun unwrap(exterior: UserName): String {
        return exterior.value
    }

    override fun wrap(interior: String): UserName {
        return UserName(interior)
    }
}
