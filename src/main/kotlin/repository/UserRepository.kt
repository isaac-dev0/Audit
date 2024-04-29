package repository

import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import domain.User
import managers.UserManager
import utility.DatabaseConnector
import org.bson.Document
import utility.DatabaseConnector.DATABASE_NAME

class UserRepository(
    private val collection: MongoCollection<User>
) : UserManager {
    companion object {
        private const val COLLECTION_NAME = "users"

        private val mongoClient: MongoClient = DatabaseConnector.getMongoClient()
        private val database: MongoDatabase = mongoClient.getDatabase(DATABASE_NAME)
        val userCollection: MongoCollection<User> =
            database.getCollection(COLLECTION_NAME, User::class.java)
    }

    override fun getUsers(): List<User> {
        return collection.find().toList()
    }

    override fun getUser(username: String): User? {
        return collection.find(Filters.eq(User::username.name, username)).firstOrNull()
    }

    override fun createUser(user: User) {
        val existingUser = getUser(user.username)
        if (existingUser != null) {
            println("User with username ${user.username} already exists.")
            return
        }

        try {
            val result = collection.insertOne(user)
            println("Success! Inserted document ID: ${result.insertedId}")
        } catch (e: MongoException) {
            println("Unable to insert due to an error: $e")
        }
    }

    override fun updateUser(user: User) {
        val filter = Filters.eq("_id", user._id)
        val updateDocument = Document(
            "\$set", Document(
                mapOf(
                    "forename" to user.forename,
                    "surname" to user.surname,
                    "username" to user.username,
                    "password" to user.password,
                    "job" to user.job,
                    "group" to user.group
                )
            )
        )
        collection.updateOne(filter, updateDocument)
    }
}