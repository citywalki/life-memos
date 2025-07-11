package app.backend.configure

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.config.propertyOrNull
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

suspend fun Application.configureJdbcDatabase() {
    var url = propertyOrNull<String>("database.url")
    var dbUser = propertyOrNull<String>("database.user")
    var dbPassword = propertyOrNull<String>("database.password")

    val dataSource = HikariDataSource().apply {
        if (developmentMode && url == null) {
            val pgsql = PostgreSQLContainer(
                DockerImageName.parse("mjquinlan2000/postgis:16-3.4-alpine")
                    .asCompatibleSubstituteFor("postgres")
            )
            pgsql.start()

            monitor.subscribe(ApplicationStopped) {
                pgsql.stop()
            }

            url = pgsql.getJdbcUrl()!!
            dbUser = pgsql.username
            dbPassword = pgsql.password
        }

        jdbcUrl = url
        username = dbUser
        password = dbPassword
        addDataSourceProperty("cachePrepStmts", "true")
        addDataSourceProperty("prepStmtCacheSize", "250")
        addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    }

//        val db = JdbcDatabase(dataSource,JdbcDialects.getByUrl(dataSource.jdbcUrl))
}
