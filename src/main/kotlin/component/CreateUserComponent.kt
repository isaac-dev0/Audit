package component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import domain.enum.Group
import domain.enum.Job
import domain.enum.Proficiency

class CreateUserComponent {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun createComponent(
        onDismissRequest: () -> Unit,
        onConfirmation: (String, String, String, String, Group, Job) -> Unit
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var forename by remember { mutableStateOf("") }
        var surname by remember { mutableStateOf("") }
        var group by remember { mutableStateOf(Group.STAFF_USER) }
        var job by remember { mutableStateOf(Job.JUNIOR_DEVELOPER) }

        var isGroupItemExpanded by remember { mutableStateOf(false) }
        var isJobItemExpanded by remember { mutableStateOf(false) }

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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text("Create User")
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        TextField(
                            value = username,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onValueChange = { username = it },
                            label = { Text("Username") }
                        )
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        TextField(
                            value = password,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onValueChange = { password = it },
                            label = { Text("Password") }
                        )
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        TextField(
                            value = forename,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onValueChange = { forename = it },
                            label = { Text("Forename") }
                        )
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        TextField(
                            value = surname,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onValueChange = { surname = it },
                            label = { Text("Surname") }
                        )
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        ExposedDropdownMenuBox(
                            expanded = isGroupItemExpanded,
                            onExpandedChange = {
                                isGroupItemExpanded = it
                            }
                        ) {
                            TextField(
                                value = group.toString(),
                                onValueChange = { },
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = isGroupItemExpanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = isGroupItemExpanded,
                                onDismissRequest = {
                                    isGroupItemExpanded = false
                                }
                            ) {
                                Group.entries.forEach { title ->
                                    DropdownMenuItem(
                                        onClick = {
                                            group = title
                                            isGroupItemExpanded = false
                                        }
                                    ) {
                                        Text(title.toString())
                                    }
                                }
                            }
                        }
                        ExposedDropdownMenuBox(
                            expanded = isJobItemExpanded,
                            onExpandedChange = {
                                isJobItemExpanded = it
                            }
                        ) {
                            TextField(
                                value = job.title,
                                onValueChange = { },
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = isJobItemExpanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = isJobItemExpanded,
                                onDismissRequest = {
                                    isJobItemExpanded = false
                                }
                            ) {
                                Job.entries.forEach { title ->
                                    DropdownMenuItem(
                                        onClick = {
                                            job = title
                                            isJobItemExpanded = false
                                        }
                                    ) {
                                        Text(title.title)
                                    }
                                }
                            }
                        }
                    }

                    item {
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
                                    onConfirmation(username, password, forename, surname, group, job)
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
}