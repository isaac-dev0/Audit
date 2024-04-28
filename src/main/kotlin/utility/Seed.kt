package utility

import domain.Skill
import domain.User
import domain.enum.Group
import domain.enum.Job
import domain.enum.Proficiency
import org.bson.types.ObjectId
import repository.SkillRepository
import repository.SkillRepository.Companion.skillCollection
import repository.UserRepository
import repository.UserRepository.Companion.userCollection

class Seed {
    fun insertUsers() {
        val userRepository = UserRepository(userCollection)
        userRepository.createUser(
            User(
                _id = ObjectId.get(),
                username = "john.smith@audit.com",
                password = "password",
                forename = "John",
                surname = "Smith",
                group = Group.MANAGER,
                skills = mutableListOf(
                    Skill(
                        _id = ObjectId.get(),
                        title = "Communication",
                        description = "Communication is an important skill!",
                        category = "Soft Skills",
                        expiryDate = "23/05/2025",
                        proficiency = Proficiency.BEGINNER,
                        notes = "I'm not great at this :/"
                    )
                ),
                parent = "",
                job = Job.SENIOR_DEVELOPER,
                children = mutableListOf(
                    "alex.grist@audit.com"
                )
            )
        )
        userRepository.createUser(
            User(
                _id = ObjectId.get(),
                username = "alex.grist@audit.com",
                password = "password",
                forename = "Alex",
                surname = "Grist",
                group = Group.STAFF_USER,
                skills = mutableListOf(
                    Skill(
                        _id = ObjectId.get(),
                        title = "Teamwork",
                        description = "Teamwork is an important skill!",
                        category = "Soft Skills",
                        expiryDate = "23/05/2025",
                        proficiency = Proficiency.BEGINNER,
                        notes = "I'm not great at this :/"
                    )
                ),
                parent = "john.smith@audit.com",
                job = Job.JUNIOR_DEVELOPER,
                children = mutableListOf()
            )
        )
    }

    fun insertSkills() {
        val skillRepository = SkillRepository(skillCollection)
        skillRepository.createSkill(
            Skill(
                _id = ObjectId.get(),
                title = "JUnit",
                description = "JUnit is a Java unit testing library.",
                category = "Java",
                expiryDate = null,
                proficiency = null,
                notes = null
            )
        )
        skillRepository.createSkill(
            Skill(
                _id = ObjectId.get(),
                title = "Microsoft Word",
                description = "Microsoft Word is a word processing tool developed by Microsoft",
                category = "Microsoft Office",
                expiryDate = null,
                proficiency = null,
                notes = null
            )
        )
    }

}