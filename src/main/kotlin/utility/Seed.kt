package utility

import domain.User
import repository.SkillRepository
import repository.UserRepository

class Seed {

    private val uuidGenerator = GenerateUUID()

    fun seedUsers() {
        val userRepository = UserRepository()
//        userRepository.addUser(User(uuidGenerator.generateNewUUID(), "john.smith@audit.com"))
//        userRepository.addUser(User(uuidGenerator.generateNewUUID(), "alex.grist@audit.com"))
    }

    fun seedSkills() {
        val skillRepository = SkillRepository()
    }
}