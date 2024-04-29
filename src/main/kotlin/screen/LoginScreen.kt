package screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
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

        var username: String by remember { mutableStateOf("") }
        var password: String by remember { mutableStateOf("") }

        val navigator = LocalNavigator.currentOrThrow
        val showErrorAlert = remember { mutableStateOf(false) }
        val userRepository = UserRepository(userCollection)
        val user = userRepository.getUser(username)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Sign in",
                textAlign = TextAlign.Left,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Welcome to the Skills Auditor.",
                textAlign = TextAlign.Left,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                modifier = Modifier
                    .background(Color(0xFF0061FF))
                    .fillMaxWidth(),
                onClick = {
                    if (user != null && user.password == password) {
                        navigator.push(ProfileScreen(user))
                    } else {
                        showErrorAlert.value = true
                    }
                }
            ) {
                Text("Sign in")
            }
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