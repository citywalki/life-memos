package app.backend.service

import app.backend.dao.SystemSettingDAO
import app.backend.dao.UserChannelDAO
import app.backend.dao.UserDAO
import app.backend.dao.def.create
import app.backend.error.authMessages
import app.backend.error.userMessages
import model.RowStatus
import model.user.ChannelName
import model.user.HashedPassword
import model.user.NickName
import model.user.User
import model.user.UserChannel
import model.user.UserChannelId
import model.user.UserId
import model.user.UserName
import model.user.UserRole
import pro.walkin.logging.I18nMessages

class AuthService(
    private val userDAO: UserDAO,
    private val userChannelDAO: UserChannelDAO,
    private val systemSettingDAOFacade: SystemSettingDAO,
) {
    suspend fun signin(
        userName: UserName,
        password: HashedPassword,
    ): User {
        val user = userDAO.findUser(userName) ?: throw IllegalStateException(I18nMessages.userMessages.userNotExist())

        systemSettingDAOFacade.findGeneralSystemSetting().apply {
            if (disallowPasswordAuth && user.role == UserRole.USER) {
                throw I18nMessages.authMessages.passwordSigninNotAllowed()
            }
        }

        if (user.status == RowStatus.ARCHIVED) {
            throw I18nMessages.authMessages.userHasBeenArchived(user.username)
        }

        if (!password.value.contentEquals(user.hashedPassword?.value)) {
            throw I18nMessages.userMessages.userPasswordNotMatch()
        }

        return user
    }

    suspend fun signup(
        userName: UserName,
        password: HashedPassword,
    ): User {
        systemSettingDAOFacade
            .findGeneralSystemSetting()
            .apply {
                check(!disallowUserRegistration) {
                    "SignUpNotAllowed"
                }
            }

        val role =
            userDAO.countUser(UserRole.HOST).let {
                if (it > 0) UserRole.USER else UserRole.HOST
            }

        val user =
            userDAO.insertUser(
                User(
                    id = UserId.Companion.create(),
                    username = userName,
                    hashedPassword = password,
                    role = role,
                    nickname = NickName(userName.value),
                ),
            )

        userChannelDAO.insertChannel(
            UserChannel(
                UserChannelId.Companion.create(),
                name = ChannelName.Companion.from("默认空间"),
                isDefaultChannel = true,
                ownerUser = user.id,
                description = null,
            ),
        )

        return user
    }

    fun getAuthStatus(): User? {
        TODO("Not yet implemented")
    }
}
