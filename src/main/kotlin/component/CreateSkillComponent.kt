package component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import domain.Skill

class CreateSkillComponent {
    @Composable
    fun createComponent(
        onDismissRequest: () -> Unit,
        onConfirmation: (String, String, String) -> Unit
    ) {
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("") }

        Dialog(
            onDismissRequest = { onDismissRequest() }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Create Skill")
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = title,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { title = it },
                        label = { Text("Title") }
                    )
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = description,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { description = it },
                        label = { Text("Description") }
                    )
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = category,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { category = it },
                        label = { Text("Category") }
                    )
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = {
                                onDismissRequest()
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("Cancel")
                        }
                        TextButton(
                            onClick = {
                                onConfirmation(title, description, category)
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("Add Skill")
                        }
                    }
                }
            }
        }

    }
}