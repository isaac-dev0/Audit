package managers

import domain.User
import domain.enum.Permission

interface UserManager {
    fun getUsers(): List<User>
    fun getUser(username: String): User?
    fun createUser(user: User)
}