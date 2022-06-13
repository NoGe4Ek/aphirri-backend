package poly.aphirri

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
    Database.connect("jdbc:postgresql://localhost:5432/aphirri?characterEncoding=utf8&useUnicode=true", password = "zZzoOo20022002")
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureSerialization()
        configureRouting()
        configureSecurity()
    }.start(wait = true)
    //EngineMain.main(args)
}