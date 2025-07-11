package pro.walkin.memos.server.usecase.user.entity

import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperExperimentalAssociation
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperManyToOne
import org.komapper.annotation.KomapperTable
import pro.walkin.memos.model.user.UserChannel

@OptIn(KomapperExperimentalAssociation::class)
@KomapperEntityDef(UserChannel::class)
@KomapperTable("memos_user_channel")
@KomapperManyToOne(targetEntity = UserTable::class)
class UserChannelTable(
    @KomapperId val uid: Nothing,
)
