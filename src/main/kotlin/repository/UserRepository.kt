package repository

import domain.User

class UserRepository {

    private val users: MutableList<User> = mutableListOf()

    fun getUsers(): List<User> {
        return users
    }

    fun getUser(username: String): User? {
        return users.find { it.username == username }
    }

    fun addUser(user: User) {
        if (users.contains(user)) {
            throw Exception("A user with these details already exists.")
        }
        users.add(user)
    }

    fun isValidCredentials(username: String, password: String): Boolean {
        return users.any { it.username == username && it.password == password }
    }

}