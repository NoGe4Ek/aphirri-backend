package poly.aphirri.features.security.register

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import poly.aphirri.database.UserDTO
import poly.aphirri.database.Users
import java.util.*

fun Route.registerRoute(secret: String, issuer: String, audience: String) {
    post("/register") {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        val userDTO = Users.fetchUser(registerReceiveRemote.username)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            Users.insert(
                UserDTO(
                    username = registerReceiveRemote.username,
                    password = registerReceiveRemote.password,
                )
            )

            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", registerReceiveRemote.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 600000))
                .sign(Algorithm.HMAC256(secret))
            call.respond(RegisterResponseRemote(token = token))
        }
    }
}