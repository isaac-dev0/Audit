package domain

import domain.enum.Proficiency
import java.util.UUID

class Skill(
    private val id: UUID,
    private val title: String,
    private val description: String,
    private val category: String,
    private val expiryDate: String,
    private val proficiency: Proficiency,
    private val notes: String
) {

    fun getId(): UUID {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getDescription(): String {
        return description
    }

    fun getCategory(): String {
        return category
    }

    fun getExpiryDate(): String {
        return expiryDate
    }

    fun getProficiency(): Proficiency {
        return proficiency
    }

    fun getNotes(): String {
        return notes
    }

}