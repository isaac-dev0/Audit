package repository

import domain.User

class UserRepository {

    private val users: MutableList<User> = mutableListOf()

    fun getUsers(): List<User> {
        return users
    }

    fun addUser(user: User) {
        if (users.contains(user)) {
            throw Exception("A user with these details already exists.")
        }
        users.add(user)
    }

}