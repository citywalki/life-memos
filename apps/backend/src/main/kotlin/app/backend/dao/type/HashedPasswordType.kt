package app.backend.dao.type

import model.user.HashedPassword
import org.komapper.core.spi.DataTypeConverter
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class HashedPasswordType : DataTypeConverter<HashedPassword, ByteArray> {
    override val exteriorType: KType = typeOf<HashedPassword>()
    override val interiorType: KType = typeOf<ByteArray>()

    override fun wrap(interior: ByteArray): HashedPassword {
        return HashedPassword(interior)
    }

    override fun unwrap(exterior: HashedPassword): ByteArray {
        return exterior.value
    }
}
