package screen

import DashboardScreen
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import domain.User
import repository.SkillRepository
import repository.UserRepository

class ProfileScreen(
    user: User?,
    skillRepository: SkillRepository?,
    userRepository: UserRepository?
) : DashboardScreen(user, skillRepository, userRepository) {
    @Composable
    override fun createScreen() {
        Text("Profile Screen")
    }

    // Create more functions here...
}