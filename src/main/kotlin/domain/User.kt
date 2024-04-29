package domain

import com.mongodb.client.model.Filters
import domain.enum.Group
import domain.enum.Job
import domain.enum.Permission
import domain.enum.Proficiency
import org.bson.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import repository.UserRepository
import repository.UserRepository.Companion.userCollection

data class User(
    @BsonId
    val _id: ObjectId? = null,
    val username: String,
    val password: String,
    val forename: String,
    val surname: String,
    val group: Group?,
    var skills: MutableList<Skill> = mutableListOf(),
    val parent: String? = "",
    val job: Job,
    val children: MutableList<String> = mutableListOf()
) {
    fun hasPermission(username: String, permission: Permission): Boolean {
        val userRepository = UserRepository(userCollection)
        val user = userRepository.getUser(username)
        return user?.let { permission in it.group!!.permissions } ?: false
    }

    fun hasSkill(username: String, skill: Skill): Boolean {
        val userRepository = UserRepository(userCollection)
        val user = userRepository.getUser(username)
        return user?.let { skill in it.skills } ?: false
    }

    fun addSkill(title: String, proficiency: Proficiency, notes: String, expiryDate: String) {
        val userRepository = UserRepository(userCollection)
        val user = userRepository.getUser(username)
        if (user != null) {
            val existingSkill = user.skills.find { it.title == title }
            if (existingSkill != null) {
                println("Skill $title already exists for user $username.")
                return
            }
            val newSkill = Skill(
                ObjectId.get(),
                title = title,
                proficiency = proficiency,
                notes = notes,
                category = null,
                description = null,
                expiryDate = expiryDate
            )
            skills.add(newSkill)
            val filter = Filters.eq("username", user.username)
            val updateDocument = Document("\$set", Document("skills", user.skills.map { skill ->
                Document("title", skill.title)
                    .append("proficiency", skill.proficiency)
                    .append("notes", skill.notes)
                    .append("expiryDate", skill.expiryDate)
            }))
            userCollection.updateOne(filter, updateDocument)
        } else {
            println("User $username not found.")
        }
    }
}