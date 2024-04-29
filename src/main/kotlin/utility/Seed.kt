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

        userRepository.createUser(
            User(
                _id = ObjectId.get(),
                username = "another.user@example.com",
                password = "password",
                forename = "Another",
                surname = "User",
                group = Group.STAFF_USER,
                skills = mutableListOf(
                    Skill(
                        _id = ObjectId.get(),
                        title = "Problem Solving",
                        description = "Problem solving is crucial in any role!",
                        category = "Soft Skills",
                        expiryDate = "23/05/2025",
                        proficiency = Proficiency.ADVANCED,
                        notes = "I excel in problem-solving tasks."
                    ),
                    Skill(
                        _id = ObjectId.get(),
                        title = "Coding",
                        description = "Proficient in various programming languages.",
                        category = "Technical Skills",
                        expiryDate = "23/05/2025",
                        proficiency = Proficiency.EXPERT,
                        notes = "I have extensive experience in coding."
                    )
                ),
                parent = "john.smith@audit.com",
                job = Job.SENIOR_DEVELOPER,
                children = mutableListOf()
            )
        )

        userRepository.createUser(
            User(
                _id = ObjectId.get(),
                username = "jane.doe@example.com",
                password = "password",
                forename = "Jane",
                surname = "Doe",
                group = Group.ADMINISTRATOR,
                skills = mutableListOf(
                    Skill(
                        _id = ObjectId.get(),
                        title = "Leadership",
                        description = "Strong leadership skills.",
                        category = "Soft Skills",
                        expiryDate = "23/05/2025",
                        proficiency = Proficiency.EXPERT,
                        notes = "I have led teams in various projects."
                    ),
                    Skill(
                        _id = ObjectId.get(),
                        title = "Project Management",
                        description = "Experienced in project management methodologies.",
                        category = "Soft Skills",
                        expiryDate = "23/05/2025",
                        proficiency = Proficiency.ADVANCED,
                        notes = "Managed multiple projects successfully."
                    )
                ),
                parent = "",
                job = Job.MID_LEVEL_DEVELOPER,
                children = mutableListOf("another.user@example.com")
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