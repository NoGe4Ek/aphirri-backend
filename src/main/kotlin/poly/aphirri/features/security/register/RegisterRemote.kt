package poly.aphirri.features.security.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val username: String,
    val password: String,
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)