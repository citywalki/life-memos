package pro.walkin.memos.messages

import pro.walkin.logging.annotations.Message
import pro.walkin.logging.annotations.MessageBundle

@MessageBundle(projectCode = "user")
interface UserMessages {
    @Message("User password not match")
    fun userPasswordNotMatch(): IllegalStateException

    @Message("已经存在相同用户")
    fun userSameExist(): String

    @Message("用户不存在")
    fun userNotExist(): String

    @Message("用户名只能是英文")
    fun usernameMustBeCharacter(): String
}
