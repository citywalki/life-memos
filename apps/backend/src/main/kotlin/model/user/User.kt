package model.user

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA512
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import model.RowStatus

@Serializable
enum class UserRole {
    ROLE_UNSPECIFIED,
    HOST,
    ADMIN,
    USER,
}

@Serializable
@JvmInline
value class UserId private constructor(val value: Long) {
    companion object {
        fun from(value: Long) = UserId(value)

        operator fun invoke(value: Long) = from(value)
        operator fun invoke(value: String) = from(value.toLong())
    }
}

@Serializable
@JvmInline
value class UserName private constructor(val value: String) {
    companion object {
        @JsonCreator
        fun from(username: String): UserName {
            return UserName(username)
        }

        operator fun invoke(username: String): UserName = from(username)
    }
}

@Serializable
@JvmInline
value class HashedPassword private constructor(val value: ByteArray) {
    companion object {
        @JsonCreator
        fun from(password: String): HashedPassword {
            val hashed = CryptographyProvider.Default
                .get(SHA512)
                .hasher()
                .hashBlocking(password.encodeToByteArray())
            return HashedPassword(hashed)
        }

        operator fun invoke(password: String) = from(password)
        operator fun invoke(password: ByteArray) = HashedPassword(password)
    }
}

@JvmInline
@Serializable
value class Email(val value: String) {
    companion object {
        fun from(email: String): Email = Email(email)
    }
}

@JvmInline
@Serializable
value class NickName(val value: String)

@Serializable
data class User(
    val id: UserId,
    val version: Int = 0,
    @JsonIgnore
    val createdAt: LocalDateTime? = null,
    @JsonIgnore
    val updatedAt: LocalDateTime? = null,
    val username: UserName,
    @Transient
    @JsonIgnore
    val hashedPassword: HashedPassword? = null,
    val status: RowStatus = RowStatus.NORMAL,
    val role: UserRole = UserRole.USER,
    val email: Email? = null,
    val nickname: NickName,
    val avatarUrl: String? = null,
)
