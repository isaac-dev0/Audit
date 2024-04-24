package domain

import domain.enum.Group
import domain.enum.Job
import java.util.UUID

class User(
    private val id: UUID,
    val username: String,
    val password: String,
    private val forename: String,
    private val surname: String,
    private val group: Group?,
    private var skills: List<String>? = mutableListOf(),
    private val parent: String? = "",
    private val job: Job,
    private val children: List<String>? = mutableListOf()
) {

    fun getId(): UUID {
        return id
    }

    fun getForename(): String {
        return forename
    }

    fun getSurname(): String {
        return surname
    }

    fun getGroup(): Group? {
        return group
    }

    fun getSkills(): List<String>? {
        return skills
    }

    fun getParent(): String? {
        return parent
    }

    fun getJob(): Job {
        return job
    }

    fun getChildren(): List<String>? {
        return children
    }

}