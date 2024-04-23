package repository

import domain.Skill

class SkillRepository {

    private val skills: MutableList<Skill> = mutableListOf()

    fun getSkills(): List<Skill> {
        return skills
    }

    fun createSkill(skill: Skill) {
        if (skills.contains(skill)) {
            throw Exception("A skill with these details already exists.")
        }
        skills.add(skill)
    }

    fun deleteSkill(skill: Skill) {
        if (!skills.contains(skill)) {
            throw Exception("A skill with these details do not exist.")
        }
        skills.remove(skill)
    }

}