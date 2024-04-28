package repository

import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import domain.User
import managers.UserManager
import utility.Constants.Companion.DATABASE_NAME
import utility.DatabaseConnector
import org.bson.Document

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

    fun updateUser(updatedUser: User) {
        val filter = Filters.eq("_id", updatedUser._id)
        val updateDocument = Document("\$set", Document(mapOf(
            "forename" to updatedUser.forename,
            "surname" to updatedUser.surname
        )))
        collection.updateOne(filter, updateDocument)
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

    fun updateUserSkills(user: User) {
        val filter = Filters.eq("_id", user._id)
        val updateDocument = Document("\$set", Document("skills", user.skills.map { skill ->
            Document("title", skill.title)
                .append("proficiency", skill.proficiency)
                .append("notes", skill.notes)
        }))
        userCollection.updateOne(filter, updateDocument)
    }
}