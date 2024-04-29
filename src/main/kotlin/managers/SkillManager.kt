package managers

import domain.Skill

interface SkillManager {
    fun getSkills(): List<Skill>
    fun getSkill(title: String): Skill?
    fun getSkillTitles(): List<String>
    fun createSkill(skill: Skill)
    fun deleteSkill(skill: Skill)
    fun updateSkill(skill: Skill)
}