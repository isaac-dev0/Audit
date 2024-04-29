package screen

import DashboardScreen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import component.CreateUserComponent
import domain.User
import domain.enum.Group
import domain.enum.Job
import domain.enum.Permission
import org.bson.types.ObjectId
import repository.UserRepository
import repository.UserRepository.Companion.userCollection

class UserScreen(
    user: User?
) : DashboardScreen(user) {
    @Composable
    override fun createScreen() {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                displayUsers()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun editUser(
        user: User,
        onUpdateUser: (User) -> Unit,
        onDismiss: () -> Unit
    ) {
        var newUsername by remember { mutableStateOf(user.username) }
        var newPassword by remember { mutableStateOf(user.password) }
        var newForename by remember { mutableStateOf(user.forename) }
        var newSurname by remember { mutableStateOf(user.surname) }
        var newGroup by remember { mutableStateOf(user.group) }
        var newJob by remember { mutableStateOf(user.job) }
        var isGroupItemExpanded by remember { mutableStateOf(false) }
        var isJobItemExpanded by remember { mutableStateOf(false) }

        Dialog(
            onDismissRequest = onDismiss
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
                        Text("Edit User")
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newUsername,
                            onValueChange = { newUsername = it },
                            label = { Text("Username") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("Password") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newForename,
                            onValueChange = { newForename = it },
                            label = { Text("Forename") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newSurname,
                            onValueChange = { newSurname = it },
                            label = { Text("Surname") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ExposedDropdownMenuBox(
                            expanded = isGroupItemExpanded,
                            onExpandedChange = {
                                isGroupItemExpanded = it
                            }
                        ) {
                            TextField(
                                value = newGroup.toString(),
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
                                            newGroup = title
                                            isGroupItemExpanded = false
                                        }
                                    ) {
                                        Text(title.toString())
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        ExposedDropdownMenuBox(
                            expanded = isJobItemExpanded,
                            onExpandedChange = {
                                isJobItemExpanded = it
                            }
                        ) {
                            TextField(
                                value = newJob.title,
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
                                            newJob = title
                                            isJobItemExpanded = false
                                        }
                                    ) {
                                        Text(title.title)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Button(
                            onClick = {
                                val updatedUser = user.copy(
                                    username = newUsername,
                                    password = newPassword,
                                    forename = newForename,
                                    surname = newSurname,
                                    group = newGroup,
                                    job = newJob
                                )
                                onUpdateUser(updatedUser)
                                onDismiss()
                            }
                        ) {
                            Text("Save Changes")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun displayUsers() {
        val userRepository = rememberSaveable { UserRepository(userCollection) }
        var isCreateUserVisible = remember { mutableStateOf(false) }
        var showUserEditDialog by remember { mutableStateOf(false) }
        val hasEditStaff by remember { mutableStateOf(user?.hasPermission(user.username, Permission.EDIT_STAFF)) }
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Users",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column {
                    if (hasEditStaff == true) {
                        TextButton(
                            onClick = {
                                isCreateUserVisible.value = true
                            }
                        ) {
                            Text("+")
                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .size(4.dp)
            )
            Divider()
            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )
            Row(
               modifier = Modifier
                   .fillMaxWidth()
            ) {
                Column {
                    Column {
                        userRepository.getUsers().forEach { user ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = "${user.forename} ${user.surname}",
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Username: ${user.username}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Job: ${user.job.title}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Group: ${user.group}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                ) {
                                    if (hasEditStaff == true) {
                                        TextButton(
                                            onClick = {
                                                showUserEditDialog = true
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (showUserEditDialog) {
            editUser(
                user = user!!,
                onUpdateUser = { updatedUser ->
                    userRepository.updateUser(updatedUser)
                },
                onDismiss = { showUserEditDialog = false }
            )
        }
        if (isCreateUserVisible.value) {
            CreateUserComponent().createComponent(
                onDismissRequest = { isCreateUserVisible.value = false },
                onConfirmation = { username, password, forename, surname, group, job ->
                    userRepository.createUser(
                        User(
                            _id = ObjectId.get(),
                            username = username,
                            password = password,
                            forename = forename,
                            surname = surname,
                            group = group,
                            skills = mutableListOf(),
                            parent = "",
                            job = job,
                            children = mutableListOf()
                        )
                    )
                    isCreateUserVisible.value = false
                }
            )
        }
    }
}