package pro.walkin.memos.server.usecase.user.entity.type

import org.komapper.core.spi.DataTypeConverter
import pro.walkin.memos.model.user.UserPassword
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class HashedPasswordType : DataTypeConverter<UserPassword, String> {
    override val exteriorType: KType = typeOf<UserPassword>()
    override val interiorType: KType = typeOf<String>()

    override fun wrap(interior: String): UserPassword = UserPassword.encode(interior)

    override fun unwrap(exterior: UserPassword): String = exterior.encodedPassword
}
