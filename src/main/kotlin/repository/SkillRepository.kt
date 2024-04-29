package repository

import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import domain.Skill
import managers.SkillManager
import org.bson.Document
import org.bson.types.ObjectId
import utility.DatabaseConnector
import utility.DatabaseConnector.DATABASE_NAME

class SkillRepository(
    private val collection: MongoCollection<Skill>
) : SkillManager {

    companion object {
        private const val COLLECTION_NAME = "skills"

        private val mongoClient: MongoClient = DatabaseConnector.getMongoClient()
        private val database: MongoDatabase = mongoClient.getDatabase(DATABASE_NAME)
        val skillCollection: MongoCollection<Skill> = database.getCollection(COLLECTION_NAME, Skill::class.java)
    }

    override fun getSkills(): List<Skill> {
        return collection.find().toList()
    }

    override fun getSkill(title: String): Skill? {
        return collection.find(Filters.eq(Skill::title.name, title)).firstOrNull()
    }

    override fun getSkillTitles(): List<String> {
        return collection.distinct("title", String::class.java).toList()
    }

    override fun createSkill(skill: Skill) {
        val existingSkill = getSkill(skill.title)
        if (existingSkill != null) {
            println("Skill with title ${skill.title} already exists.")
            return
        }

        try {
            val result = collection.insertOne(skill)
            println("Success! Inserted document ID: " + result.insertedId)
        } catch (e: MongoException) {
            println("Unable to insert due to an error: $e")
        }
    }

    override fun deleteSkill(skill: Skill) {
        skill._id?.let { collection.deleteOneById(it) }
    }

    override fun updateSkill(skill: Skill) {
        val filter = Filters.eq("_id", skill._id)
        val updateDocument = Document("\$set", Document(mapOf(
            "title" to skill.title,
            "description" to skill.description,
            "category" to skill.category
        )))
        collection.updateOne(filter, updateDocument)
    }

    private fun MongoCollection<Skill>.deleteOneById(id: ObjectId) {
        this.findOneAndDelete(Document("_id", id))
    }
}