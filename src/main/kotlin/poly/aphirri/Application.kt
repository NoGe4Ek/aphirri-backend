package poly.aphirri

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import poly.aphirri.features.hello.configureRouting
import poly.aphirri.features.security.configureSecurity
import poly.aphirri.plugins.configureSerialization

//fun main() {
//    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
//        configureRouting()
//        configureSecurity()
//    }.start(wait = true)
//}

fun main(args: Array<String>) {
    val config = HikariConfig("hikari.properties")
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    //Database.connect("jdbc:postgresql://localhost:5432/aphirri?characterEncoding=windows-1251&useUnicode=true", user = "postgres", password = "zZzoOo20022002")
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureSerialization()
        configureRouting()
        configureSecurity()
    }.start(wait = true)
    //EngineMain.main(args)
}