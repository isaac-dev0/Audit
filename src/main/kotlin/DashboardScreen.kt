import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import component.NavigationComponent
import domain.User
import repository.SkillRepository
import repository.UserRepository
import screen.LoginScreen
import screen.ProfileScreen
import screen.SkillScreen
import screen.UserScreen

abstract class DashboardScreen(
    private val user: User,
    private val skillRepository: SkillRepository?,
    private val userRepository: UserRepository?
) : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val items = listOf(
            Pair(Icons.Default.Home, "Profile"),
            Pair(Icons.Default.Person, "Users"),
            Pair(Icons.Default.Build, "Skills"),
            Pair(Icons.Default.ExitToApp, "Logout")
        )

        val onItemClick: (String) -> Unit = { clickedItem ->
            when (clickedItem) {
                "Home" -> navigator.push(ProfileScreen(user, skillRepository, userRepository))
                "Users" -> navigator.push(UserScreen(user, skillRepository, userRepository))
                "Skills" -> navigator.push(SkillScreen(user, skillRepository, userRepository))
                "Logout" -> navigator.push(LoginScreen())
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
            ) {
                NavigationComponent().createComponent(items, onItemClick)
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    createScreen()
                }
            }
        }
    }

    @Composable
    abstract fun createScreen()
}