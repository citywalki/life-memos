package pro.walkin.memos.server.usecase.user

import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import pro.walkin.logging.I18nMessages
import pro.walkin.memos.messages.authMessages
import pro.walkin.memos.messages.userMessages
import pro.walkin.memos.model.RowStatus
import pro.walkin.memos.model.user.ChannelName
import pro.walkin.memos.model.user.NickName
import pro.walkin.memos.model.user.User
import pro.walkin.memos.model.user.UserChannel
import pro.walkin.memos.model.user.UserChannelId
import pro.walkin.memos.model.user.UserId
import pro.walkin.memos.model.user.UserName
import pro.walkin.memos.model.user.UserPassword
import pro.walkin.memos.server.common.IdWorker
import pro.walkin.memos.server.usecase.system.SystemSettingQuery
import pro.walkin.memos.server.usecase.user.entity.user

@Service
class UserService(
    private val userQuery: UserQuery,
    private val db: R2dbcDatabase,
    private val systemSettingQuery: SystemSettingQuery,
    private val userChannelService: UserChannelService,
) {
    private val u = Meta.user

    suspend fun signinCheck(
        userName: UserName,
        password: UserPassword,
    ): User {
        val user = userQuery.findUser(userName) ?: throw IllegalStateException(I18nMessages.userMessages.userNotExist())

        systemSettingQuery.findGeneralSystemSetting().apply {
            if (disallowPasswordAuth && user.role == User.UserRole.USER) {
                throw I18nMessages.authMessages.passwordSigninNotAllowed()
            }
        }

        if (user.status == RowStatus.ARCHIVED) {
            throw I18nMessages.authMessages.userHasBeenArchived(user.username)
        }

        if (password.encodedPassword != user.userPassword?.encodedPassword) {
            throw I18nMessages.userMessages.userPasswordNotMatch()
        }

        return user
    }

    suspend fun signup(
        userName: UserName,
        password: UserPassword,
    ): User {
        systemSettingQuery
            .findGeneralSystemSetting()
            .apply {
                check(!disallowUserRegistration) {
                    "SignUpNotAllowed"
                }
            }

        val role =
            userQuery.countUser(User.UserRole.HOST).let {
                if (it > 0) User.UserRole.USER else User.UserRole.HOST
            }

        val user =
            insertUser(
                User(
                    uid = UserId(IdWorker.DEFAULT.nextId()),
                    username = userName,
                    userPassword = password,
                    role = role,
                    nickname = NickName(userName.value),
                ),
            )

        userChannelService.insertChannel(
            UserChannel(
                UserChannelId(IdWorker.DEFAULT.nextId()),
                name = ChannelName.Companion.from("默认空间"),
                isDefaultChannel = true,
                ownerUser = user.uid,
                description = null,
            ),
        )

        return user
    }

    private suspend fun insertUser(user: User): User {
        val query = QueryDsl.insert(u).single(user)

        return db.runQuery(query)
    }
}
