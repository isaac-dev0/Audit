package utility

import domain.User
import domain.enum.Group
import domain.enum.Job
import repository.SkillRepository
import repository.UserRepository

class Seed {

    private val uuidGenerator = GenerateUUID()

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