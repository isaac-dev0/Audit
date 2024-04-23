import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import component.ErrorAlertComponent

@Composable
@Preview
fun App() {

    val showErrorComponent = remember { mutableStateOf(false) }

    MaterialTheme {
        Button(
            onClick = { showErrorComponent.value = true }
        ) {
            Text("Click Me!")
        }

        if (showErrorComponent.value) {
            ErrorAlertComponent().createComponent(
                onDismissRequest = { showErrorComponent.value = false },
                message = "This is an error."
            )
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
