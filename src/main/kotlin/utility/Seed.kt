package utility

import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import domain.User
import domain.enum.Group
import domain.enum.Job
import org.bson.Document
import repository.SkillRepository
import repository.UserRepository

class Seed {

    private val uuidGenerator = GenerateUUID()

    private val mongoClient = DatabaseConnector.getMongoClient()
    private val database: MongoDatabase = mongoClient.getDatabase("audit")

    fun insertUsers() {
        val collection: MongoCollection<Document> = database.getCollection("users")

        val user = Document("username", "john.smith@audit.com")
            .append("password", "root")

        collection.insertOne(user)

        mongoClient.close()
    }

    fun insertSkills() {
        val collection: MongoCollection<Document> = database.getCollection("skills")

        val skill = Document("title", "JUnit")
            .append("category", "Java")

        collection.insertOne(skill)

        mongoClient.close()
    }

    fun seedUsers() {
        val userRepository = UserRepository()
        userRepository.addUser(
            User(
                uuidGenerator.generateNewUUID(),
                "john.smith@audit.com",
                "password",
                "John",
                "Smith",
                Group.MANAGER,
                listOf(
                    "Communication"
                ),
                "",
                Job.SENIOR_DEVELOPER,
                listOf(
                    "alex.grist@audit.com"
                )
            )
        )
        userRepository.addUser(
            User(
                uuidGenerator.generateNewUUID(),
                "alex.grist@audit.com",
                "password",
                "Alex",
                "Grist",
                Group.STAFF_USER,
                listOf(
                    "Teamwork"
                ),
                "john.smith@audit.com",
                Job.JUNIOR_DEVELOPER
            )
        )
    }

    fun seedSkills() {
        val skillRepository = SkillRepository()
    }
}