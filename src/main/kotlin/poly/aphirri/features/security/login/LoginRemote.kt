package poly.aphirri.features.security.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveRemote(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponseRemote(
    val token: String
)