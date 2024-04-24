package screen

import DashboardScreen
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import domain.User
import repository.SkillRepository
import repository.UserRepository

class UserScreen(
    user: User,
    skillRepository: SkillRepository?,
    userRepository: UserRepository?
) : DashboardScreen(user, skillRepository, userRepository) {
    @Composable
    override fun createScreen() {
        Text("User Screen")
    }

    // Create more functions here...
}