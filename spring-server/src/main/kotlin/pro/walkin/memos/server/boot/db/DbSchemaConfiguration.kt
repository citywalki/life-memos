package pro.walkin.memos.server.boot.db

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import pro.walkin.memos.server.usecase.system.entity.systemSetting
import pro.walkin.memos.server.usecase.user.entity.user
import pro.walkin.memos.server.usecase.user.entity.userChannel
import pro.walkin.memos.server.usecase.user.entity.userFollowChannel
import pro.walkin.memos.server.usecase.user.entity.userSetting

@Configuration
@Profile("dev")
class DbSchemaConfiguration {
    @OptIn(DelicateCoroutinesApi::class)
    @Bean
    fun initDbSchema(db: R2dbcDatabase): CommandLineRunner =
        CommandLineRunner {
            GlobalScope.launch {
                db.runQuery {
                    QueryDsl.Companion.drop(
                        Meta.user,
                        Meta.userChannel,
                        Meta.systemSetting,
                        Meta.userSetting,
                        Meta.userFollowChannel,
                    )
                }
                db.runQuery {
                    QueryDsl.Companion.create(
                        Meta.user,
                        Meta.userChannel,
                        Meta.systemSetting,
                        Meta.userSetting,
                        Meta.userFollowChannel,
                    )
                }
            }
        }
}
