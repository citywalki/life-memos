package app.backend

import app.backend.configure.configureController
import app.backend.configure.configureDAO
import app.backend.configure.configureDatabase
import app.backend.configure.configureDependencies
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain
        .main(args)
}

suspend fun Application.module() {
    configureDatabase()
    configureDAO()
    configureDependencies()

    configureController()
}
