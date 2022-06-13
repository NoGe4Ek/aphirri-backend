package poly.aphirri.features.security.login

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import poly.aphirri.database.Users
import poly.aphirri.features.security.register.RegisterReceiveRemote
import java.util.*

fun Route.loginRoute(secret: String, issuer: String, audience: String) {
    post("/login") {
        lateinit var loginReceiveRemote: LoginReceiveRemote
        try {
            loginReceiveRemote = call.receive()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.PreconditionFailed, "Required parameter is missing")
        }
        val userDTO = Users.fetchUser(loginReceiveRemote.username)

        if (userDTO != null) {
            if (userDTO.password == loginReceiveRemote.password) {
                val token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("username", loginReceiveRemote.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 600000))
                    .sign(Algorithm.HMAC256(secret))
                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.Forbidden, "Password is incorrect")
            }
        } else {
                call.respond(HttpStatusCode.NotFound, "User doesn't exists")
        }
    }
}