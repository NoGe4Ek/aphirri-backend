package poly.aphirri.util

import io.ktor.server.application.*


val Application.secret: String
    get() = environment.config.property("jwt.secret").getString()