package pro.walkin.memos.server.usecase.user.entity

import org.komapper.annotation.KomapperCreatedAt
import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import org.komapper.annotation.KomapperUpdatedAt
import org.komapper.annotation.KomapperVersion
import pro.walkin.memos.model.user.User

@KomapperEntityDef(User::class)
@KomapperTable("memos_user")
data class UserTable(
    @KomapperId val uid: Nothing,
    @KomapperVersion val version: Nothing,
    @KomapperCreatedAt val createdAt: Nothing,
    @KomapperUpdatedAt val updatedAt: Nothing,
)
