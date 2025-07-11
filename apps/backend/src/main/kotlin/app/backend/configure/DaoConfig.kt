package app.backend.configure

import app.backend.dao.SystemSettingDAO
import app.backend.dao.UserChannelDAO
import app.backend.dao.UserDAO
import app.backend.dao.UserFollowChannelDAO
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

fun Application.configureDAO() {
    dependencies {
        provide { UserDAO(resolve()) }
        provide { UserChannelDAO(resolve()) }
        provide { SystemSettingDAO(resolve()) }
        provide { UserFollowChannelDAO(resolve()) }
    }
}
