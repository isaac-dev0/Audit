import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import screen.LoginScreen
import utility.Seed

@Composable
@Preview
fun App() {

    val seed = Seed()

    seed.insertUsers()
    seed.insertSkills()

    MaterialTheme {
        Navigator(LoginScreen())
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
