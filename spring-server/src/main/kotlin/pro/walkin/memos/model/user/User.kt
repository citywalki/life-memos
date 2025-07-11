package pro.walkin.memos.model.user

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pro.walkin.logging.I18nMessages
import pro.walkin.memos.messages.userMessages
import pro.walkin.memos.model.RowStatus
import java.time.LocalDateTime

@JvmInline
value class UserId private constructor(
    val value: Long,
) {
    companion object {
        fun from(value: Long) = UserId(value)

        operator fun invoke(value: Long) = from(value)

        operator fun invoke(value: String) = from(value.toLong())
    }
}

@JvmInline
value class UserPassword private constructor(
    val encodedPassword: String,
) {
    companion object {
        val passwordEncoder = BCryptPasswordEncoder(13)

        @JsonCreator
        @JvmStatic
        fun raw(
            @JsonProperty password: String,
        ): UserPassword {
            val pwd = UserPassword(passwordEncoder.encode(password))

            return pwd
        }

        fun encode(encodedPassword: String): UserPassword = UserPassword(encodedPassword)

        operator fun invoke(password: String) = raw(password)
    }
}

@JvmInline
value class Email(
    val value: String,
) {
    companion object {
        fun from(email: String): Email = Email(email)
    }
}

@JvmInline
value class NickName(
    val value: String,
)

@JvmInline
value class UserName private constructor(
    val value: String,
) {
    companion object {
        private val USER_NAME_REGEX = Regex("[A-Za-z]+")

        @JsonCreator
        @JvmStatic
        fun from(username: String): UserName = UserName(username)

        operator fun invoke(username: String): UserName = from(username)
    }

    init {
        validateUserName(value)
    }

    private fun validateUserName(username: String) {
        require(USER_NAME_REGEX.matches(username)) { I18nMessages.userMessages.usernameMustBeCharacter() }
    }
}

data class User(
    val uid: UserId,
    val version: Int = 0,
    @JsonIgnore
    val createdAt: LocalDateTime? = null,
    @JsonIgnore
    val updatedAt: LocalDateTime? = null,
    val username: UserName,
    @Transient
    @JsonIgnore
    val userPassword: UserPassword? = null,
    val status: RowStatus = RowStatus.NORMAL,
    val role: UserRole = UserRole.USER,
    val email: Email? = null,
    val nickname: NickName,
    val avatarUrl: String? = null,
) {
    enum class UserRole {
        ROLE_UNSPECIFIED,
        HOST,
        ADMIN,
        USER,
    }
}
