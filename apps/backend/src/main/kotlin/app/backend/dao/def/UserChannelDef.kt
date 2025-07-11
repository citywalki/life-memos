package app.backend.dao.def

import model.user.UserChannel
import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperExperimentalAssociation
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperManyToOne
import org.komapper.annotation.KomapperTable

@OptIn(KomapperExperimentalAssociation::class)
@KomapperEntityDef(UserChannel::class)
@KomapperTable("memos_user_channel")
@KomapperManyToOne(targetEntity = UserDef::class)
class UserChannelDef(
    @KomapperId val uid: Nothing
)
