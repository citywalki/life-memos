package pro.walkin.memos.server.usecase.user.entity.type

import org.komapper.core.spi.DataTypeConverter
import pro.walkin.memos.model.user.UserName
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class UserNameType : DataTypeConverter<UserName, String> {
    override val exteriorType: KType = typeOf<UserName>()
    override val interiorType: KType = typeOf<String>()

    override fun unwrap(exterior: UserName): String = exterior.value

    override fun wrap(interior: String): UserName = UserName(interior)
}
