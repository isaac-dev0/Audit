import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class DashboardScreen : Screen {
    @Composable
    override fun Content() {
        Text("Dashboard Screen")
    }
}