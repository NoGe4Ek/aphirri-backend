package poly.aphirri.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.util.PSQLException

object Users : Table("users") {
    private val username = Users.varchar("username", 25)
    private val password = Users.varchar("password", 25)

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[username] = userDTO.username
                it[password] = userDTO.password
            }
        }
    }

    fun fetchUser(username: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.select { Users.username.eq(username) }.singleOrNull()
                userModel?.let {
                    UserDTO(
                        username = userModel[Users.username],
                        password = userModel[password],
                    )
                }
            }
        } catch (e: PSQLException) {
            e.message
            null
        }
    }
}