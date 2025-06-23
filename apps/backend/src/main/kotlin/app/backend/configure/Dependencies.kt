package app.backend.configure

import app.backend.domain.auth.AuthService
import app.backend.domain.system.persistence.SystemSettingDAOFacadeKomapper
import app.backend.domain.user.persistence.UserDAOKomapper
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

fun Application.configureDependencies() {
    dependencies {
        provide { UserDAOKomapper(resolve()) }
        provide { SystemSettingDAOFacadeKomapper(resolve()) }
        provide {
            AuthService(
                resolve(),
                resolve(),
                resolve(),
            )
        }
    }
}
