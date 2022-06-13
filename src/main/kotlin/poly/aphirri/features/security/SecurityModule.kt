package poly.aphirri.features.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import poly.aphirri.features.security.login.loginRoute
import poly.aphirri.features.security.register.registerRoute

fun Application.configureSecurity() {
    val secret = "secret"//environment.config.property("jwt.secret").getString()
    val issuer = "http://0.0.0.0:8080/"//environment.config.property("jwt.issuer").getString()
    val audience = "http://0.0.0.0:8080/hello"//environment.config.property("jwt.audience").getString()
    val myRealm = "Access to 'hello'"//environment.config.property("jwt.realm").getString()

    authentication {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build())
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

    routing {
        registerRoute(secret, issuer, audience)
        loginRoute(secret, issuer, audience)
    }
}