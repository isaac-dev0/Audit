package screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import component.AlertComponent
import repository.UserRepository
import repository.UserRepository.Companion.userCollection

class LoginScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        var username: String by remember { mutableStateOf("") }
        var password: String by remember { mutableStateOf("") }

        val showErrorAlert = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .padding(8.dp)
            )
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            Spacer(
                modifier = Modifier
                    .padding(8.dp)
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(
                modifier = Modifier
                    .padding(8.dp)
            )
            Button(
                onClick = {
                    val userRepository = UserRepository(userCollection)
                    val user = userRepository.getUser(username)
                    if (user != null && user.password == password) {
                        navigator.push(ProfileScreen(user))
                    } else {
                        showErrorAlert.value = true
                    }
                }
            ) {
                Text("Login")
            }

            if (showErrorAlert.value) {
                AlertComponent().createComponent(
                    message = "You have provided invalid login details.",
                    onDismissRequest = {
                        showErrorAlert.value = false
                    }
                )
            }
        }
    }
}