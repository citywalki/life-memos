package app.backend.configure

import app.backend.service.AuthService
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

fun Application.configureDependencies() {
    dependencies {
        provide {
            AuthService(
                resolve(),
                resolve(),
                resolve(),
            )
        }
    }
}
