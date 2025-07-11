package pro.walkin.memos.server.usecase.user

import kotlinx.coroutines.reactor.mono
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.operator.count
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import pro.walkin.memos.model.user.Email
import pro.walkin.memos.model.user.User
import pro.walkin.memos.model.user.UserId
import pro.walkin.memos.model.user.UserName
import pro.walkin.memos.server.usecase.user.entity.user
import reactor.core.publisher.Mono

@Service
class UserQuery(
    private val db: R2dbcDatabase,
) : ReactiveUserDetailsService,
    UserDetailsService {
    private val u = Meta.user

    suspend fun countUser(role: User.UserRole): Long {
        val query =
            QueryDsl
                .from(u)
                .where {
                    u.role eq role
                }.select(count(u.uid))
                .map { it ?: 0 }

        return db.runQuery(query)
    }

    suspend fun findUser(id: UserId): User? {
        val query =
            QueryDsl
                .from(u)
                .where {
                    u.uid eq id
                }.singleOrNull()

        return db.runQuery(query)
    }

    suspend fun findUser(username: UserName): User? {
        val query =
            QueryDsl
                .from(u)
                .where {
                    u.username eq username
                }.singleOrNull()

        return db.runQuery(query)
    }

    suspend fun findUser(email: Email): User? {
        val query =
            QueryDsl
                .from(u)
                .where {
                    u.email eq email
                }.singleOrNull()

        return db.runQuery(query)
    }

    suspend fun findUsers() =
        db.runQuery {
            QueryDsl.from(u)
        }

    override fun findByUsername(username: String?): Mono<UserDetails?>? =
        mono {
            val userName = username?.let { UserName(it) } ?: return@mono null
            findUser(userName)?.let {
                org.springframework.security.core.userdetails
                    .User(it.username.value, it.userPassword!!.encodedPassword, emptySet())
            }
        }

    override fun loadUserByUsername(username: String?): UserDetails? = findByUsername(username)?.block()
}
